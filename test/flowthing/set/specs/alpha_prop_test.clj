(ns flowthing.set.specs.alpha-prop-test
  (:require [clojure.set :as set]
            [clojure.test.check.clojure-test :refer [defspec]]
            [clojure.test.check.properties :as prop]
            [clojure.test.check.generators :as gen]
            [com.gfredericks.test.chuck.generators :as gen*])
  (:refer-clojure :exclude [identity]))

(def ^:dynamic *iterations* 100)

(def ^:dynamic *set-gen* (gen/set gen/int))

(defspec union-commutative
  *iterations*
  (prop/for-all [a *set-gen*
                 b *set-gen*]
    (= (set/union a b)
       (set/union b a))))

(defspec intersection-commutative
  (prop/for-all [a *set-gen*
                 b *set-gen*]
    (= (set/intersection a b)
       (set/intersection b a))))

(defspec union-associative
  *iterations*
  (prop/for-all [a *set-gen*
                 b *set-gen*
                 c *set-gen*]
    (= (set/union a (set/union b c))
       (set/union c (set/union a b)))))

(defspec intersection-associative
  *iterations*
  (prop/for-all [a *set-gen*
                 b *set-gen*
                 c *set-gen*]
    (= (set/intersection a (set/intersection b c))
       (set/intersection c (set/intersection a b)))))

(defspec distributive-union
  *iterations*
  (prop/for-all [a *set-gen*
                 b *set-gen*
                 c *set-gen*]
    (= (set/union a (set/intersection b c))
       (set/intersection (set/union a b) (set/union a c)))))

(defspec distributive-intersection
  *iterations*
  (prop/for-all [a *set-gen*
                 b *set-gen*
                 c *set-gen*]
    (= (set/intersection a (set/union b c))
       (set/union (set/intersection a b) (set/intersection a c)))))

(defspec identity-union
  *iterations*
  (gen/let [u *set-gen*]
    (prop/for-all [a (gen*/subset u)]
      (= (set/union a #{})
         a))))

(defspec identity-intersection
  *iterations*
  (gen/let [u *set-gen*]
    (prop/for-all [a (gen*/subset u)]
      (= (set/intersection a u)
         a))))

(defspec idempotent-union
  *iterations*
  (prop/for-all [a *set-gen*]
    (= (set/union a a)
       a)))

(defspec idempotent-intersection
  *iterations*
  (prop/for-all [a *set-gen*]
    (= (set/intersection a a)
       a)))

(defspec domination-union
  *iterations*
  (gen/let [u *set-gen*]
    (prop/for-all [a (gen*/subset u)]
      (= (set/union a u)
         u))))

(defspec domination-intersection
  *iterations*
  (prop/for-all [a *set-gen*]
    (= (set/intersection a #{})
       #{})))

(defspec absorption-union
  *iterations*
  (prop/for-all [a *set-gen*
                 b *set-gen*]
    (= (set/union a (set/intersection a b))
       a)))

(defspec absorption-intersection
  *iterations*
  (prop/for-all [a *set-gen*
                 b *set-gen*]
    (= (set/intersection a (set/union a b))
       a)))
