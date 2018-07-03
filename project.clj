(defproject me.flowthing/set.specs.alpha "0.1.0-SNAPSHOT"

  :description "Specs for the functions in clojure.set."

  :url "http://www.github.com/eerohele/flowthing.set.specs.alpha"

  :license {:name "Eclipse Public License"

            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.9.0"]]

  :profiles {:dev {:dependencies [[org.clojure/test.check "0.9.0"]
                                  [com.gfredericks/test.chuck "0.2.9"]
                                  [expound "0.6.0"]]}})
