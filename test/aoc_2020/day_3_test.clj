(ns aoc-2020.day-3-test
  (:require [aoc-2020.day-3 :as day-3]
            [clojure.test :refer [deftest is]]))

(def map-input
  "..##.......
   #...#...#..
   .#....#..#.
   ..#.#...#.#
   .#...##..#.
   ..#.##.....
   .#.#.#....#
   .#........#
   #.##...#...
   #...##....#
   .#..#...#.#")

(def parsed-map-input
  [[:snow :snow :tree :tree :snow :snow :snow :snow :snow :snow :snow]
   [:tree :snow :snow :snow :tree :snow :snow :snow :tree :snow :snow]
   [:snow :tree :snow :snow :snow :snow :tree :snow :snow :tree :snow]
   [:snow :snow :tree :snow :tree :snow :snow :snow :tree :snow :tree]
   [:snow :tree :snow :snow :snow :tree :tree :snow :snow :tree :snow]
   [:snow :snow :tree :snow :tree :tree :snow :snow :snow :snow :snow]
   [:snow :tree :snow :tree :snow :tree :snow :snow :snow :snow :tree]
   [:snow :tree :snow :snow :snow :snow :snow :snow :snow :snow :tree]
   [:tree :snow :tree :tree :snow :snow :snow :tree :snow :snow :snow]
   [:tree :snow :snow :snow :tree :tree :snow :snow :snow :snow :tree]
   [:snow :tree :snow :snow :tree :snow :snow :snow :tree :snow :tree]])

(deftest parse-map-input-test
  (is (= parsed-map-input (day-3/parse-map-input map-input))))

(deftest path-terrain-test
  (let [expected-path-terrain [:snow :snow :tree :snow :tree :tree :snow :tree :tree :tree :tree]]
    (is (= expected-path-terrain (day-3/path-terrain parsed-map-input [3 1])))))

(deftest calculate-trees-on-slope-test
  (let [parsed-map (day-3/parse-map-input map-input)]
    (is (= 7 (day-3/calculate-trees-on-slope parsed-map [3 1])))
    (is (= 2 (day-3/calculate-trees-on-slope parsed-map [1 2])))))
