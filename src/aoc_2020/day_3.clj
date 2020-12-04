(ns aoc-2020.day-3
  (:require [clojure.java.io :as io]
            [cuerdas.core :as str]))

(def input
  (slurp (io/resource "day_3_input.txt")))

(def terrain-lookup
  {\. :snow
   \# :tree})

(defn parse-map-line
  [map-line]
  (mapv terrain-lookup map-line))

(defn parse-map-input
  [map-input]
  (->> map-input
       (str/lines)
       (mapv str/trim)
       (mapv parse-map-line)))

(def parsed-map-input
  (parse-map-input input))

(defn path-through-map
  [parsed-map slope-x slope-y]
  (let [map-width  (count (first parsed-map))
        map-height (count parsed-map)
        path-fn    (fn [[x y]]
                     [(mod (+ x slope-x) map-width)
                      (+ y slope-y)])]
    (->> (iterate path-fn [0 0])
         (take-while #(<= (last %) (dec map-height))))))

(defn terrain-at-point
  [parsed-map map-point]
  (let [map-x (first map-point)
        map-y (second map-point)]
    (nth (nth parsed-map map-y) map-x)))

(defn path-terrain
  [parsed-map slope]
  (let [[slope-x slope-y] slope
        path              (path-through-map parsed-map slope-x slope-y)]
    (map (partial terrain-at-point parsed-map) path)))


;; Part 1

(defn calculate-trees-on-slope
  [parsed-map slope]
  (->> (path-terrain parsed-map slope)
       (filter #(= :tree %))
       (count)))

;; Part 2

(def slope-list
  [[1 1]
   [3 1]
   [5 1]
   [7 1]
   [1 2]])

(defn calculate-tree-product-for-slopes
  [parsed-map slopes]
  (apply * (pmap #(calculate-trees-on-slope parsed-map %) slopes)))
