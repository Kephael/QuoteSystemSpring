'use strict';

angular.module('myApp.common', ['ui.router'])

//ROUTER
    .config(['$stateProvider', function($stateProvider) {
        $stateProvider.state('app', {
            url: '/app',
            templateUrl: 'modules/common/view/globalActions.html',
            controller: 'appCtrl',
            controllerAs: 'appCtrl'
        });
    }])

    //CONTROLLER
    .controller('appCtrl', [ 'Navigation', '$state', '$rootScope', 'userService', 'credentialsService', function(navigation, $state, $scope, userService, credentialsService) {

        this.$scope = $scope;

        this.navigationOptions = navigation.options;
        this.$state = $state;
        this.userService = userService;

        this.userRoles = userService.getUserRoles();

        /**
         * Navigate to given state
         *
         * @param state
         */
        this.goTo= function(state) {
            this.$state.go(state)
        };

        this.isAuthorized = function(authorizedRoles) {

            var authorized = false;

            this.userRoles.forEach(function(role) {
                if ( authorizedRoles.includes(role) ) {
                    authorized = true;
                    return;
                }
            });

            return authorized;
        };

        this.logout = function() {
            credentialsService.clearCredentials();
            userService.clearUser();

            this.goTo("login");
        }
    }])

    //Rest Utility Constants
    .constant("RestUtil", {
        "URL": "http://localhost:8080"
    })

    .constant("Navigation", {
        "options": [
            {
                state: "app.home",
                stateName: "HOME",
                authorized: ['ROLE_ADMIN', 'ROLE_USER']
            },
            {
                state: "app.quotes",
                stateName: "QUOTES",
                authorized: ['ROLE_ADMIN', 'ROLE_USER']
            },
            {
                state: "app.forms",
                stateName: "FORMS",
                authorized: ['ROLE_ADMIN']
            }
        ]
    })
    .service('notificationService', ['$rootScope', '$mdToast', function($rootScope, $mdToast) {

        this.$rootScope = $rootScope;
        this.$mdToast = $mdToast;

        this.notify = function(message) {
            this.$rootScope.$broadcast('notification', { message: message });

            this.$mdToast.show(
                this.$mdToast.simple()
                    .textContent(message)
                    .position('bottom left')
                    .hideDelay(3000)
            );
        }
    }])
    .service('userService', ['$rootScope', '$window', function($rootScope, $window) {

        this.$rootScope = $rootScope;
        this.$window = $window;

        this.userRoles;

        this.username;


        this.getUserRoles = function() {
            if(!this.userRoles){
                this.userRoles = this.$window.localStorage.getItem('userRoles').split(",");
            }

            return this.userRoles;
        };

        this.setUserRoles = function(roles) {
            this.$window.localStorage.setItem('userRoles', roles);
            this.userRoles = roles;
        };

        this.getUsername = function() {
            if(!this.username){
                this.username = this.$window.localStorage.getItem('username');
            }

            return this.username;
        };

        this.setUsername = function(username) {
            this.$window.localStorage.setItem('username', username);
            this.username = username;
        };

        this.clearUser = function() {

        }
    }])
    .service('credentialsService', [ '$window', function($window) {

        this.clearCredentials = function() {
            $window.localStorage.removeItem('basicCredentials');
        };

        this.setCredentials = function(username, password) {
            this.clearCredentials();
            $window.localStorage.setItem('basicCredentials', btoa(username + ':' + password));
        };

        this.getCredentials = function() {
            return $window.localStorage.getItem('basicCredentials');
        }
    }])
    .run(['$http', 'credentialsService', function($http, credentialsService) {
        $http.defaults.headers.common['Authorization'] = 'Basic ' + credentialsService.getCredentials();
    }])
    .config(function($httpProvider) {
        $httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
        $httpProvider.defaults.useXDomain = true;
    });