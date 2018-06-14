(ns flowthing.set.specs.alpha
  (:require [clojure.set :as set]
            [clojure.spec.alpha :as s]))

(s/def ::nullary
  (s/cat))

(s/def ::unary
  (s/cat :s1 set?))

(s/def ::binary
  (s/cat :s1 set? :s2 set?))

(s/def ::variadic
  (s/cat :s1 set? :s2 set? :sets (s/* set?)))

(s/fdef set/union
  :args (s/alt :nullary ::nullary
               :unary ::unary
               :binary ::binary
               :variadic ::variadic)
  :ret set?)

(s/fdef set/intersection
  :args (s/alt :unary ::unary
               :binary ::binary
               :variadic ::variadic)
  :ret set?)

(s/fdef set/difference
  :args (s/alt :unary ::unary
               :binary ::binary
               :variadic ::variadic)
  :ret set?)

(s/def ::pred
  (s/fspec :args (s/cat :any any?)
           :ret boolean?))

(s/fdef set/select
  :args (s/cat :pred ::pred
               :xset set?)
  :ret set?)

(s/fdef set/project
  :args (s/cat :xrel (s/coll-of map?)
               :ks sequential?)
  :ret set?)

(s/fdef set/rename-keys
  :args (s/cat :map map?
               :kmap map?)
  :ret map?)

(s/fdef set/rename
  :args (s/cat :xrel (s/coll-of map?)
               :kmap map?)
  :ret set?)

(s/fdef set/index
  :args (s/cat :xrel (s/coll-of map?)
               :ks sequential?)
  :ret map?)

(s/fdef set/map-invert
  :args (s/cat :m map?)
  :ret map?)

(s/fdef set/join
  :args (s/alt :binary (s/cat :xrel (s/coll-of map?)
                              :yrel (s/coll-of map?))
               :ternary (s/cat :xrel (s/coll-of map?)
                               :yrel (s/coll-of map?)
                               :km map?)))

(s/fdef set/subset?
  :args (s/cat :set1 set?
               :set2 set?)
  :ret boolean?)

(s/fdef set/superset?
  :args (s/cat :set1 set?
               :set2 set?)
  :ret boolean?)
