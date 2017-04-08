'use strict';

// Declare app level module which depends on views, and components
angular.module('myApp', [
  'ngRoute',
  'ui.router',
  'myApp.login',
  'myApp.common',
  'myApp.quotes',
  'myApp.version',
  'myApp.landing',
  'myApp.restMock',
  'myApp.forms',
  'ngMaterial',
  'mdSteppers'
]).
config(['$stateProvider', '$urlRouterProvider', function($stateProvider, $urlRouterProvider) {
  $urlRouterProvider.otherwise('/login');
}]).
config(function($mdThemingProvider) {
  $mdThemingProvider.theme('default')
      .primaryPalette('blue-grey')
      .accentPalette('indigo');
});
// .config(function($mdIconProvider) {
//   $mdIconProvider
//       .icon('computer-off', 'icons/ic_desktop_windows_grey_48px.svg', 60)
//       .icon('computer-on', 'icons/ic_desktop_windows_green_48px.svg', 60)
// });