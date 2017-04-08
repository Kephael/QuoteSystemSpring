'use strict';

angular.module('myApp.landing', ['ui.router', 'myApp.common'])

//ROUTER
    .config(['$stateProvider', function($stateProvider) {
        $stateProvider.state('app.home', {
            url: '/home',
            templateUrl: 'modules/landing/view/landing.html',
            controller: 'homeCtrl',
            controllerAs: 'ctrl'
        });
    }])

    //CONTROLLER
    .controller('homeCtrl', ['$state', function($state) {

        this.getAQuote = function(){
            $state.go("app.formsUserSubmission");
        }

    }]);