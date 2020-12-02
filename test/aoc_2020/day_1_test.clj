(ns aoc-2020.day-1-test
  (:require [aoc-2020.day-1 :as day-1]
            [clojure.test :refer [deftest is]]))


(deftest n-tuple-with-target-sum-test
  (let [test-input [1 4 8 9 15]]
    (is (= [1 15] (day-1/n-tuple-with-target-sum test-input 2 16)))))

;; Part 1

(deftest product-of-pair-with-2020-sum-test
  (let [test-input [1721 979 366 299 675 1456]]
    (is (= 514579 (day-1/product-of-pair-with-2020-sum test-input)))))

;; Part 2

(deftest product-of-3-tuple-with-2020-sum-test
  (let [test-input [1721 979 366 299 675 1456]]
    (is (= 241861950 (day-1/product-of-3-tuple-with-2020-sum test-input)))))
