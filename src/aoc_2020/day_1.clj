(ns aoc-2020.day-1
  (:require [clojure.math.combinatorics :as combo]
            [clojure.java.io :as io]
            [cuerdas.core :as str]))


(def input
  (slurp (io/resource "day_1_input.txt")))

(def parsed-entries
  (->> input
       (str/lines)
       (map #(Integer. %))))

(defn n-tuple-with-target-sum
  [entries n target-sum]
  (let [target-sum-fn (fn [tuple] (= target-sum (apply + tuple)))
        combinations  (combo/combinations entries n)]
    (first (filter target-sum-fn combinations))))

;; Part 1

(defn product-of-pair-with-2020-sum
  [entries]
  (apply * (n-tuple-with-target-sum entries 2 2020)))


;; Part 2

(defn product-of-3-tuple-with-2020-sum
  [entries]
  (apply * (n-tuple-with-target-sum entries 3 2020)))
