(ns aoc-2020.day-9
  (:require [clojure.java.io :as io]
            [clojure.math.combinatorics :as combo]
            [cuerdas.core :as str]))

(def data-input
  (slurp (io/resource "day_9_input.txt")))

(defn parse-data
  [input]
  (->> (str/lines input)
       (mapv #(str/parse-long (str/trim %)))))

(def parsed-data-input
  (parse-data data-input))

(defn window
  [data window-size start]
  (take window-size (drop start data)))

(defn combination-sums
  [preamble]
  (reduce
   (fn [sums combination]
     (conj sums (apply + combination)))
   #{}
   (combo/combinations preamble 2)))

(defn valid-at-index?
  [data preamble-size index]
  (assert (and (< index (count data))
               (>= index preamble-size)))
  (let [preamble (window data preamble-size (- index preamble-size))
        sums     (combination-sums preamble)
        value    (nth data index)]
    (contains? sums value)))

(defn first-invalid-value
  [data preamble-size]
  (let [valid-fn      (partial valid-at-index? data preamble-size)
        index-range   (range preamble-size (count data))
        invalid-index (first (remove valid-fn index-range))]
    (nth data invalid-index)))

(defn calculate-first-invalid-value
  [data]
  (first-invalid-value data 25))

(defn all-windows
  [data]
  (let [data-size (count data)]
    (for [window-size (range 2 data-size)
          :let
          [range-max (inc (- data-size window-size))]
          window-start (range range-max)]
      (window data window-size window-start))))

(defn encryption-weakness
  [data preamble-size]
  (let [invalid-value (first-invalid-value data preamble-size)]
    (loop [windows (all-windows data)]
      (let [[window & remaining-windows] windows]
        (cond
          (not (seq window))
          nil

          (= invalid-value (apply + window))
          (+ (apply min window) (apply max window))

          :else
          (recur remaining-windows))))))


(defn calculate-xmas-weakness
  [data]
  (encryption-weakness data 25))
