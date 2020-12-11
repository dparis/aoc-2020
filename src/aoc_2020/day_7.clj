(ns aoc-2020.day-7
  (:require [clojure.java.io :as io]
            [cuerdas.core :as str]
            [ubergraph.alg :as ua]
            [ubergraph.core :as u]))

(def bag-rules-input
  (slurp (io/resource "day_7_input.txt")))

(defn container->kw
  [container]
  (keyword (str/replace container " " "-")))

(defn parse-contents
  [contents]
  (if (= contents "no other bags")
    {}
    (let [content-parts (str/split contents ", ")]
      (reduce
       (fn [parsed part]
         (let [[_ num container] (re-find #"(\d+)\s+(.*?)\s+(bag[s]?)" part)
               container-kw      (container->kw container)]
           (assoc parsed container-kw {:count (str/parse-long num)})))
       {}
       content-parts))))

(defn parse-rule
  [line]
  (let [[_ container contents] (re-find #"^\s*(.*?)\s*bags contain\s*(.*?)\.\s*$" line)
        container-kw           (container->kw container)
        parsed-contents        (parse-contents contents)]
    {container-kw parsed-contents}))

(defn parse-bag-rules
  [input]
  (reduce
   (fn [rules line]
     (merge rules (parse-rule line)))
   {}
   (str/lines input)))

(def parsed-bag-rules
  (parse-bag-rules bag-rules-input))

(defn unweighted-bag-digraph
  [bag-rules]
  (let [adj-map (reduce
                 (fn [m [bag adj]]
                   (assoc m bag adj))
                 {}
                 bag-rules)]
    (u/digraph adj-map)))

(defn bags-containing
  [bag-graph target-bag]
  (-> (u/transpose bag-graph)
      (ua/post-traverse target-bag)
      (butlast)
      (set)))

;; Part 1

(defn calculate-shiny-gold-containers
  [bag-rules]
  (let [graph (unweighted-bag-digraph bag-rules)]
    (count (bags-containing graph :shiny-gold))))


;; Part 2

(defn total-count-in-bag
  [bag-graph target-bag]
  (reduce
   (fn [total-count cur-edge]
     (let [cur-count  (u/attr bag-graph cur-edge :count)
           next-bag   (u/dest cur-edge)
           sub-bags   (total-count-in-bag bag-graph next-bag)]
       (+ total-count cur-count (* cur-count sub-bags))))
   0
   (u/out-edges bag-graph target-bag)))

(defn calculate-shiny-gold-contents-count
  [bag-rules]
  (let [graph (unweighted-bag-digraph bag-rules)]
    (total-count-in-bag graph :shiny-gold)))
