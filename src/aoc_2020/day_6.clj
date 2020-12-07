(ns aoc-2020.day-6
  (:require [cuerdas.core :as str]
            [clojure.java.io :as io]))


(def form-group-input
  (slurp (io/resource "day_6_input.txt")))

(defn trimmed-lines
  [s]
  (map str/trim (str/lines s)))

(defn parse-form-groups
  [input]
  (->> (str/split input #"\s*\n{2}+\s*")
       (map trimmed-lines)))

(def parsed-form-group-input
  (parse-form-groups form-group-input))

(defn form-group-answer-set
  [form-group]
  (reduce
   (fn [answer-set form]
     (into answer-set (map str form)))
   #{}
   form-group))


;; Part 1

(defn calculate-positive-answer-count
  [parsed-form-groups]
  (->> (map form-group-answer-set parsed-form-groups)
       (map count)
       (reduce +)))


;; Part 2

(defn form-group-unanimous-answers
  [form-group]
  (let [group-size    (count form-group)
        answer-counts (->> (mapcat str/chars form-group)
                           (frequencies))]
    (->> (filter #(= group-size (second %)) answer-counts)
         (into {}))))


(defn calculate-unanimous-answer-count
  [parsed-form-groups]
  (->> (map form-group-unanimous-answers parsed-form-groups)
       (map count)
       (reduce +)))
