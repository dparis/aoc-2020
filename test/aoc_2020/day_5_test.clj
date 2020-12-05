(ns aoc-2020.day-5-test
  (:require [aoc-2020.day-5 :as day-5]
            [clojure.test :refer [deftest is]]
            [cuerdas.core :as str]))


(def passes-input
  "FBFBBFFRLR
   BFFFBBFRRR
   FFFBBBFRRR
   BBFFBBFRLL")

(def parsed-passes-input
  ["FBFBBFFRLR"
   "BFFFBBFRRR"
   "FFFBBBFRRR"
   "BBFFBBFRLL"])

(deftest parse-passes
  (is (= parsed-passes-input (day-5/parse-passes passes-input))))

(deftest decode-row-test
  (let [rows (mapv #(subs % 0 7) parsed-passes-input)]
    (is (= [44 70 14 102] (mapv day-5/decode-row rows)))))

(deftest decode-column-test
  (let [columns (mapv #(subs % 7) parsed-passes-input)]
    (is (= [4 6 6 4] (mapv day-5/decode-column columns)))))

(def decoded-passes-input
  [{:row     44
    :column  5
    :seat-id 357}
   {:row     70
    :column  7
    :seat-id 567}
   {:row     14
    :column  7
    :seat-id 119}
   {:row     102
    :column  4
    :seat-id 820}])

(deftest decode-pass-test
  (is (= decoded-passes-input (mapv day-5/decode-pass parsed-passes-input))))
