(ns image-slider.app
  (:require [reagent.core :as r]))

(defn url [src]
  (str "url(" js/window.location.href src))

(def images
  [{:src "images/GPTempDownload.jpg"
    :strava-link "https://www.strava.com/activities/4939151368"
    :iframe-link "https://www.strava.com/activities/4939151368/embed/b3b00155f0da277fabb0b4f5a58e6b884a25d482"}
   {:src "images/GPTempDownload3.jpg"
    :strava-link "https://www.strava.com/activities/4504203195"
    :iframe-link "https://www.strava.com/activities/4504203195/embed/8c0ecc6fad7f354cb46f225944280330c353077d"}
   {:src "images/GPTempDownload4.jpg"
    :strava-link "https://www.strava.com/activities/4318304953"
    :iframe-link "https://www.strava.com/activities/4318304953/embed/4181ff10926c13085108f5fe15579884e4aed9ff"}
   {:src "images/GPTempDownload5.jpg"
    :strava-link "https://www.strava.com/activities/3960006159"
    :iframe-link "https://www.strava.com/activities/3960006159/embed/e738a34d72f5fb210167dea19ce22fcdd9d37475"}])

(def interval-time 500)

(def eraserActiveTime 700)

(defonce image-index (r/atom 0))
(defonce interval (r/atom nil))
(def eraser-class (r/atom ""))

(defn- set-interval []
  (js/setInterval
    (fn []
      (reset! eraser-class "active")
      (js/setTimeout
        (fn []
          (reset! eraser-class "")
          (if-not (= @image-index (dec (count images)))
            (swap! image-index inc)
            (reset! image-index 0)))
        interval-time))
    5000))

(defn- manage-timer [callback]
  (reset! eraser-class "active")
  (js/setTimeout (fn []
                   (reset! eraser-class "")
                   (callback))
                 eraserActiveTime)
  (js/clearTimeout @interval)                               ;Stop interval
  (reset! interval (set-interval))                          ;Start interval again
  )

(defn- to-next-image [image-index]
  (if-not (= @image-index (dec (count images)))
    (swap! image-index inc)
    (reset! image-index 0)))

(defn- slide-active []
  [:div.slide.active
   {:style {:background-image (url (get-in images [@image-index :src]))}}])

(defn- strava-acvity-container []
  (let [{:keys [strava-link iframe-link]} (get images @image-index)]
    (prn "iframe-link " iframe-link)
    [:div.iframe-container
     [:iframe.strava {:frameborder "0"
                      :src iframe-link}]
     [:a.strava-badge-.strava-badge-follow
      {:href strava-link
       :target "_blank"}
      [:img {:src "//badges.strava.com/echelon-sprite-32.png"
             :alt "Strava"}]]]))

(defn- previous-btn []
  [:button#previous
   {:style {:background-image (url "/icons/caret_left.svg")}
    :on-click (fn []
                (manage-timer
                  #(if-not (= @image-index 0)
                     (swap! image-index dec)
                     (reset! image-index (dec (count images))))))}])

(defn- next-btn []
  [:button#next
   {:style {:background-image (url "/icons/caret_right.svg")}
    :on-click (fn []
                (manage-timer
                  #(to-next-image image-index)))}])

(defn- slider []
  [:div.slider
   [slide-active]
   [strava-acvity-container]
   [:div.buttons-container
    [previous-btn]
    [next-btn]]
   [:div.eraser {:class @eraser-class}]])

(defn- initial-state []
  (reset! image-index 0)
  (some-> @interval (js/clearTimeout))
  (reset! interval (set-interval)))

(defn core []
  (initial-state)
  (fn []
    [slider]))