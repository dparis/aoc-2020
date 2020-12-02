(ns aoc-2020.core
  (:require [aoc-2020.day-1 :as day-1]
            [clojure.pprint :as pp])
  (:gen-class))

(defn ^:private build-result
  [day first-answer second-answer]
  {"Day"                day
   "First Star Answer"  first-answer
   "Second Star Answer" second-answer})

(defn ^:private day-1-result
  []
  (build-result
   1
   (day-1/product-of-pair-with-2020-sum day-1/parsed-entries)
   (day-1/product-of-3-tuple-with-2020-sum day-1/parsed-entries)))

(defn ^:private print-result-table
  []
  (let [results (->> (vector (future (day-1-result)))
                     (mapv deref))]
    (pp/print-table ["Day" "First Star Answer" "Second Star Answer"] results)))

(defn -main
  [& args]
  (time
   (print-result-table)))
