flowthing.set.specs.alpha
=========================

Specs for the functions in `clojure.set`.

## Example

```clojure
(require '[clojure.set :as set])

(set/union #{:a} [:b :c])
;;=> [:b :c :a] ;; gahh

(require '[clojure.spec.test.alpha :as spec.test]
         '[flowthing.set.specs.alpha])

(spec.test/instrument)
(set/union #{:a} [:b :c])

;;=>
;; CompilerException clojure.lang.ExceptionInfo: Call to #'clojure.set/union did not conform to spec:
;;
;; -- Spec failed --------------------
;;
;; Function arguments
;;
;; (#{:a} [:b :c])
;;        ^^^^^^^
;;
;;     should satisfy
;;
;; set?
;;
;; -------------------------
 ```

## License

Copyright © 2018 Eero Helenius

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
