'use strict';

angular.module('myApp.quotes', ['ui.router', 'myApp.common'])

//ROUTER
    .config(['$stateProvider', function($stateProvider) {
        $stateProvider.state('app.quotes', {
            url: "/quotes",
            templateUrl: 'modules/quotes/view/quotes.html',
            controller: 'quotesCtrl',
            controllerAs: 'ctrl'
        });
    }])

    //CONTROLLER
    .controller('quotesCtrl', ['Navigation', '$state', 'quotesService', function(navigation, $state, service){

        this.navigationOptions = navigation.options;
        this.currentlySelectedIndex=0;
        this.data = {};

        this.types= [
            'Dropdown',
            'Check Boxes',
            'Radio Buttons',
            'Text Input',
            'Large Input'
        ];

        /**
         * Navigate to given state
         *
         * @param state
         */
        this.goTo= function(state) {
            $state.go(state)
        };

        /**
         * fetchs form data from backend api
         *
         * @param ctrl - reference to current controller must be passed in since inner function has isolated scope
         * @returns {*} - form data
         */
        this.getForm = function(ctrl) {
            return service.getForm()
                .then(function successCallback(response){
                    console.log("Success");
                    ctrl.data = response.data;
                }, function errorCallback(){
                    console.log("Error: Failed to retrieve from server");
                });
        };

        this.createForm = function() {
          return service.createForm(this.data)
              .then(function successCallback(response){
                  console.log("Created Successfully");
              }, function errorCallback(error){
                  console.log("Validation error occurred");
              })
        };

        /**
         * indicates the selected question
         *
         * @param stepIndex
         * @param index
         */
        this.setSelectedIndex = function(stepIndex, index) {
            console.log(this.data);
            this.data.steps[stepIndex].questions[this.currentlySelectedIndex].selectedComponent = false;
            this.data.steps[stepIndex].questions[index].selectedComponent = true;
            this.currentlySelectedIndex = index;
        };

        this.addQuestionBelow = function(stepIndex, index) {
            this.data.steps[stepIndex].questions.splice(index + 1, 0,
                {
                    label: "",
                    type: "Text Input",
                    position: index + 1,
                    value: null
                });
            this.currentlySelectedIndex = index + 1;
            this.data.steps[stepIndex].questions[index + 1].selectedComponent = true;
        };

        this.deleteQuestion = function(stepIndex, index){
            this.data.steps[stepIndex].questions.splice(index, 1);
            if(this.currentlySelectedIndex == this.data.steps[stepIndex].questions.length){
                this.currentlySelectedIndex--;
                this.data.steps[stepIndex].questions[index - 1].selectedComponent = true;
            }else {
                this.data.steps[stepIndex].questions[index].selectedComponent = true;
            }
        };

        this.shiftUp = function(stepIndex, index) {

            var temp = this.data.steps[stepIndex].questions[index];
            this.data.steps[stepIndex].questions[index] = this.data.steps[stepIndex].questions[index - 1];
            this.data.steps[stepIndex].questions[index - 1] = temp;
            this.currentlySelectedIndex = index - 1;

        };

        this.shiftDown = function(stepIndex, index){

            var temp = this.data.steps[stepIndex].questions[index];
            this.data.steps[stepIndex].questions[index] = this.data.steps[stepIndex].questions[index + 1];
            this.data.steps[stepIndex].questions[index + 1] = temp;
            this.currentlySelectedIndex = index + 1;

        };

        /**
         * Last question cannot be shifted down
         *
         * @param stepIndex
         * @param index
         * @returns {boolean}
         */
        this.disableShiftDown = function(stepIndex, index){
            return index == (this.data.steps[stepIndex].questions.length - 1);
        };

        /**
         * First question cannot be shifted up
         *
         * @param index
         * @returns {boolean}
         */
        this.disableShiftUp = function(index){
            return index == 0;
        };

        /**
         * At least one question must always be available
         *
         * @param stepIndex
         * @returns {boolean}
         */
        this.disableDelete = function(stepIndex){
            return this.data.steps[stepIndex].questions.length == 1;
        };

    }])
    .service('quotesService', ['$http', 'RestUtil', function($http, restUtil) {

        /**
         * Post request to server in order to retrieve form data
         *
         * @returns {HttpPromise} - Promise that resolves to http response
         */
        this.getForm = function() {

            this.$http = $http;

            var url = restUtil.URL + '/form';

            return this.$http.get(url);
        };

        /**
         * Post request to server in order to authenticate the user
         *
         * @param username - the username
         * @param password - the password
         * @returns {HttpPromise} - Promise that resolves to http response
         */
        this.submitForm = function(data) {

            this.$http = $http;

            var url = restUtil.URL + '/form';
            var data = data;

            return this.$http.post(url, data);
        }
    }]);