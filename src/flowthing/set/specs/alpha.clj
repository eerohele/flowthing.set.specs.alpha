(ns flowthing.set.specs.alpha
  (:require [clojure.set :as set]
            [clojure.spec.alpha :as s]))

(s/def ::nilable-set
  (s/nilable set?))

(s/def ::nilable-map
  (s/nilable map?))

(s/def ::rel
  (s/nilable (s/coll-of ::nilable-map)))

(s/def ::nullary
  (s/cat))

(s/def ::unary
  (s/cat :s1 ::nilable-set))

(s/def ::binary
  (s/cat :s1 ::nilable-set
         :s2 ::nilable-set))

(s/def ::variadic
  (s/cat :s1 ::nilable-set
         :s2 ::nilable-set
         :sets (s/* ::nilable-set)))

(s/fdef set/union
  :args (s/alt :nullary ::nullary
               :unary ::unary
               :binary ::binary
               :variadic ::variadic)
  :ret ::nilable-set)

(s/fdef set/intersection
  :args (s/alt :unary ::unary
               :binary ::binary
               :variadic ::variadic)
  :ret ::nilable-set)

(s/fdef set/difference
  :args (s/alt :unary ::unary
               :binary ::binary
               :variadic ::variadic)
  :ret ::nilable-set)

(s/def ::pred
  (s/fspec :args (s/cat :any any?)
           :ret any?))

(s/fdef set/select
  :args (s/cat :pred ::pred
               :xset ::nilable-set)
  :ret ::nilable-set)

(s/fdef set/project
  :args (s/cat :xrel ::rel
               :ks sequential?)
  :ret set?)

(s/fdef set/rename-keys
  :args (s/cat :map ::nilable-map
               :kmap ::nilable-map)
  :ret ::nilable-map)

(s/fdef set/rename
  :args (s/cat :xrel ::rel
               :kmap ::nilable-map)
  :ret set?)

(s/fdef set/index
  :args (s/cat :xrel ::rel
               :ks sequential?)
  :ret ::nilable-map)

(s/fdef set/map-invert
  :args (s/cat :m ::nilable-map)
  :ret map?)

(s/fdef set/join
  :args (s/alt :binary (s/cat :xrel ::rel
                              :yrel ::rel)
               :ternary (s/cat :xrel ::rel
                               :yrel ::rel
                               :km ::nilable-map)))

(s/fdef set/subset?
  :args (s/cat :set1 ::nilable-set
               :set2 ::nilable-set)
  :ret boolean?)

(s/fdef set/superset?
  :args (s/cat :set1 ::nilable-set
               :set2 ::nilable-set)
  :ret boolean?)
