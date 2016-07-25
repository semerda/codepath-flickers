# Week 1 - *Flickster*

**Flickster** shows the latest movies currently playing in theaters. The app utilizes the Movie Database API to display images and basic information about these movies to the user.

Time spent: **∞** hours spent in total

## User Stories

The following **required** functionality is completed:

* [x] User can **scroll through current movies** from the Movie Database API
* [x] Layout is optimized with the [ViewHolder](http://guides.codepath.com/android/Using-an-ArrayAdapter-with-ListView#improving-performance-with-the-viewholder-pattern) pattern.
* [x] For each movie displayed, user can see the following details:
  * [x] Title, Poster Image, Overview (Portrait mode)
  * [x] Title, Backdrop Image, Overview (Landscape mode)

The following **optional** features are implemented:

* [x] User can **pull-to-refresh** popular stream to get the latest movies.
* [x] Display a nice default [placeholder graphic](http://guides.codepath.com/android/Displaying-Images-with-the-Picasso-Library#configuring-picasso) for each image during loading.
* [x] Improved the user interface through styling and coloring.

The following **bonus** features are implemented:

* [x] Allow user to view details of the movie including ratings and popularity within a separate activity or dialog fragment.
* [x] When viewing a popular movie (i.e. a movie voted for more than 5 stars) the video should show the full backdrop image as the layout.  Uses [Heterogenous ListViews](http://guides.codepath.com/android/Implementing-a-Heterogenous-ListView) or [Heterogenous RecyclerView](http://guides.codepath.com/android/Heterogenous-Layouts-inside-RecyclerView) to show different layouts.
* [x] Allow video trailers to be played in full-screen using the YouTubePlayerView.
    * [x] Overlay a play icon for videos that can be played.
    * [x] More popular movies should start a separate activity that plays the video immediately.
    * [x] Less popular videos rely on the detail page should show ratings and a YouTube preview.
* [ ] Leverages the [data binding support module](http://guides.codepath.com/android/Applying-Data-Binding-for-Views) to bind data into layout templates.
* [x] Apply the popular [Butterknife annotation library](http://guides.codepath.com/android/Reducing-View-Boilerplate-with-Butterknife) to reduce boilerplate code.
* [x] Apply rounded corners for the poster or background images using [Picasso transformations](https://guides.codepath.com/android/Displaying-Images-with-the-Picasso-Library#other-transformations)

The following **additional** features are implemented:

* [x] Search movie database from the ActionBar
* [x] ActionBar auto hides when scrolling
* [x] Using Constants class to store API keys
* [x] [Endless Scrolling with AdapterViews](https://github.com/codepath/android_guides/wiki/Endless-Scrolling-with-AdapterViews-and-RecyclerView)
* [x] Detail view uses RatingBar and ProgressBar to illustrate the value
* [x] Genres displayed using dynamic string identifiers to pull in the name from string resources
* [x] [Using the CardView](https://github.com/codepath/android_guides/wiki/Using-the-CardView) for cells
* [x] Added ScrollView for RelativeLayout in DetailActivity

## Video Walkthrough

Here's a walkthrough of implemented user stories:
<img src='https://github.com/semerda/codepath-flickers/blob/master/misc/flickster-anim.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Open-source libraries used

- [Android Async HTTP](https://github.com/loopj/android-async-http) - Simple asynchronous HTTP requests with JSON parsing
- [YouTubeAndroidPlayerApi](http://guides.codepath.com/android/Streaming-Youtube-Videos-with-YouTubePlayerView) - Streaming Youtube Videos with YouTubePlayerView
- [Picasso](http://square.github.io/picasso/) - Image loading and caching library for Android
- [Butter Knife](https://github.com/JakeWharton/butterknife) - Bind Android views and callbacks to fields and methods.

## License

    Copyright 2016 Ernest Semerda (http://www.ernestsemerda.com/)

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
