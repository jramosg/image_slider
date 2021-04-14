(ns image-slider.hello
  (:require [reagent.core :as r]))

(defn click-counter [click-count]
  [:div
   "The atom " [:code "click-count"] " has value: "
   @click-count ". "
   [:input {:type "button" :value "Click me!"
            :on-click #(swap! click-count inc)}]])

(def images ["/images/GPTempDownload.JPG"])


(defn app []
      [:div.slider
       [:div.slide.active
        ;{:src "/images/GPTempDownload.JPG"}
        {:style {:background-image (str "url('" js/window.location.href "images/GPTempDownload.JPG'")}}
        ]
       [:div.buttons-container
        [:button#previous
         {:style {:background-image (str "url('" js/window.location.href "/icons/caret_left.svg")}}]
        [:button#next {:style {:background-image (str "url('" js/window.location.href "/icons/caret_right.svg")}}]]])
"background-image: url('https://images.unsplash.com/photo-1431444393712-19267bd26144?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1191&q=80');"