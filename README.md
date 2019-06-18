# Android GraphQL sample project with Apollo
Sample project that uses Apollo and RxJava to query some basic information from the GitHub GraphQL API
<br>
About the GitHub API: https://github.blog/2016-09-14-the-github-graphql-api/
About the Apollo for Android: https://www.apollographql.com/docs/android/
<br>
<b>Query used</b>
<br>
<pre>
query {
  user(login:"jakewharton") {
    name
    bio
    location
    starredRepositories {
      totalCount
    }
  }
}
</pre>
<br>
<b>Installation</b>
GitHub API v4 (https://api.github.com/graphql) requires the user to be authenticated.<br>
This project uses the GIT_TOKEN property from the global gradle properties file. <br> 
Please add the following to the gradle.properties file in your <User>/.gradle/ folder (create a new one if it's not there): <br>
GIT_TOKEN="[your GitHub access token]"


