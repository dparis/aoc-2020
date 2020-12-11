(ns aoc-2020.core
  (:require [aoc-2020.day-1 :as day-1]
            [aoc-2020.day-2 :as day-2]
            [aoc-2020.day-3 :as day-3]
            [aoc-2020.day-4 :as day-4]
            [aoc-2020.day-5 :as day-5]
            [aoc-2020.day-6 :as day-6]
            [aoc-2020.day-7 :as day-7]
            [aoc-2020.day-8 :as day-8]
            [aoc-2020.day-9 :as day-9]
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

(defn ^:private day-2-result
  []
  (build-result
   2
   (day-2/valid-password-count day-2/parsed-pw-rules)
   (day-2/valid-positional-password-count day-2/parsed-pw-rules)))

(defn ^:private day-3-result
  []
  (build-result
   3
   (day-3/calculate-trees-on-slope day-3/parsed-map-input [3 1])
   (day-3/calculate-tree-product-for-slopes day-3/parsed-map-input day-3/slope-list)))

(defn ^:private day-4-result
  []
  (build-result
   4
   (day-4/calculate-valid-entries-1 day-4/parsed-batch-input)
   (day-4/calculate-valid-entries-2 day-4/parsed-batch-input)))

(defn ^:private day-5-result
  []
  (build-result
   5
   (day-5/calculate-highest-seat-id day-5/decoded-passes-input)
   (day-5/calculate-your-seat-id day-5/decoded-passes-input)))

(defn ^:private day-6-result
  []
  (build-result
   6
   (day-6/calculate-positive-answer-count day-6/parsed-form-group-input)
   (day-6/calculate-unanimous-answer-count day-6/parsed-form-group-input)))

(defn ^:private day-7-result
  []
  (build-result
   7
   (day-7/calculate-shiny-gold-containers day-7/parsed-bag-rules)
   (day-7/calculate-shiny-gold-contents-count day-7/parsed-bag-rules )))

(defn ^:private day-8-result
  []
  (build-result
   8
   (day-8/calculate-value-before-loop day-8/parsed-code-input)
   (day-8/calculate-fixed-code-value day-8/parsed-code-input)))

(defn ^:private day-9-result
  []
  (build-result
   9
   (day-9/calculate-first-invalid-value day-9/parsed-data-input)
   (day-9/calculate-xmas-weakness day-9/parsed-data-input)))

(defn ^:private print-result-table
  []
  (let [results (->> (vector (future (day-1-result))
                             (future (day-2-result))
                             (future (day-3-result))
                             (future (day-4-result))
                             (future (day-5-result))
                             (future (day-6-result))
                             (future (day-7-result))
                             (future (day-8-result))
                             (future (day-9-result)))
                     (mapv deref))]
    (pp/print-table ["Day" "First Star Answer" "Second Star Answer"] results)))

(defn -main
  [& args]
  (time
   (print-result-table)))
