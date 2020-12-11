(ns aoc-2020.day-7-test
  (:require [aoc-2020.day-7 :as day-7]
            [clojure.test :refer [deftest is]]
            [ubergraph.core :as u]))

(def bag-rules
  "light red bags contain 1 bright white bag, 2 muted yellow bags.
   dark orange bags contain 3 bright white bags, 4 muted yellow bags.
   bright white bags contain 1 shiny gold bag.
   muted yellow bags contain 2 shiny gold bags, 9 faded blue bags.
   shiny gold bags contain 1 dark olive bag, 2 vibrant plum bags.
   dark olive bags contain 3 faded blue bags, 4 dotted black bags.
   vibrant plum bags contain 5 faded blue bags, 6 dotted black bags.
   faded blue bags contain no other bags.
   dotted black bags contain no other bags.")

(def parsed-bag-rules
  {:vibrant-plum {:faded-blue   {:count 5}
                  :dotted-black {:count 6}}
   :light-red    {:bright-white {:count 1}
                  :muted-yellow {:count 2}}
   :shiny-gold   {:dark-olive   {:count 1}
                  :vibrant-plum {:count 2}}
   :dark-orange  {:bright-white {:count 3}
                  :muted-yellow {:count 4}}
   :bright-white {:shiny-gold {:count 1}}
   :muted-yellow {:shiny-gold {:count 2}
                  :faded-blue {:count 9}}
   :faded-blue   {}
   :dark-olive   {:faded-blue   {:count 3}
                  :dotted-black {:count 4}}
   :dotted-black {}})

(deftest parse-bag-rules-test
  (is (= parsed-bag-rules (day-7/parse-bag-rules bag-rules))))

(def bag-digraph
  (day-7/unweighted-bag-digraph parsed-bag-rules))

(deftest bags-containing-test
  (is (= #{:dark-orange :light-red :muted-yellow :bright-white}
         (day-7/bags-containing bag-digraph :shiny-gold))))

(deftest total-count-in-bag-test
  (is (= 32 (day-7/total-count-in-bag bag-digraph :shiny-gold))))
