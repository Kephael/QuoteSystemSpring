'use strict';

angular.module('myApp.forms', ['ui.router', 'myApp.common'])

//ROUTER
    .config(['$stateProvider', function($stateProvider) {
        $stateProvider.state('app.forms', {
            url: "/forms",
            templateUrl: 'modules/forms/view/forms-list.html',
            controller: 'formsCtrl',
            controllerAs: 'ctrl'
        });

        $stateProvider.state('app.formsCreate', {
            url: "/formsCreate",
            templateUrl: 'modules/forms/view/form.html',
            controller: 'formsCtrl',
            controllerAs: 'ctrl'
        });

        $stateProvider.state('app.formsEdit', {
            url: "/formsCreate",
            templateUrl: 'modules/forms/view/form.html',
            controller: 'formsCtrl',
            controllerAs: 'ctrl'
        });

        $stateProvider.state('app.formsUserSubmission', {
            url: "/form/submission",
            templateUrl: 'modules/forms/view/userForm.html',
            controller: 'formsCtrl',
            controllerAs: 'ctrl'
        });
    }])

    //CONTROLLER
    .controller('formsCtrl', ['Navigation', '$state', 'formsService', 'notificationService', 'userService' , function(navigation, $state, service, notificationService, userService){

        this.currentlySelectedIndex=0;

        this.mode;

        this.allForms = [];

        this.form = {};
        this.notificationService = notificationService;

        this.types= [
            'DROPDOWN',
            'CHECKBOX',
            'RADIO',
            'SHORT_RESPONSE',
            'LONG_RESPONSE'
        ];

        this.initializeNewTemplate = function() {
            this.form = service.initForm();
        };

        this.getViewableForms =function(ctrl) {
            return service.getFormsByUsername(userService.getUsername())
                .then(function successCallback(response){
                    console.log("Successfully Retrieved Forms");
                    notificationService.notify("Successfully Retrieved Forms");
                    ctrl.allForms = response.data;
                }, function errorCallback(){
                    notificationService.notify("Error: Failed to retrieve from server");
                });
        };

        this.goToCreate = function() {
            $state.go("app.formsCreate");
        };

        this.toEditForm = function(id) {
            $state.go("app.formsEdit");
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
                    notificationService.notify("Success");
                    ctrl.form = response.data;
                }, function errorCallback(){
                    notificationService.notify("Error: Failed to retrieve from server");
                });
        };

        this.createForm = function() {
            return service.submitForm(this.form)
                .then(function successCallback(response){
                    notificationService.notify("Created Successfully");
                    $state.go("app.forms")
                }, function errorCallback(error){
                    notificationService.notify("Validation error occurred");
                })
        };

        this.deleteForm = function(id) {
            return service.deleteForm(this.form)
                .then(function successCallback(response){
                    notificationService.notify("Deleted Successfully");
                }, function errorCallback(error){
                    notificationService.notify("Error");
                })
        };

        /**
         * indicates the selected question
         *
         * @param stepIndex
         * @param index
         */
        this.setSelectedIndex = function(index) {
            console.log(this.form);
            console.log(index);

            console.log("selectedIndex", this.currentlySelectedIndex);

            this.form.questions[this.currentlySelectedIndex].selectedComponent = false;
            this.form.questions[index].selectedComponent = true;
            this.currentlySelectedIndex = index;
        };

        this.addQuestionBelow = function(index) {
            this.form.questions.splice(index + 1, 0,
                {
                    prompt: "",
                    type: "SHORT_RESPONSE",
                    value: [],
                    weight: 1,
                    valueWeight: 1
                });
            this.currentlySelectedIndex = index + 1;
            this.form.questions[index + 1].selectedComponent = true;
        };

        this.deleteQuestion = function(index){
            this.form.questions.splice(index, 1);
            if(this.currentlySelectedIndex == this.form.questions.length){
                this.currentlySelectedIndex--;
                this.form.questions[index - 1].selectedComponent = true;
            }else {
                this.form.questions[index].selectedComponent = true;
            }
        };

        this.shiftUp = function(index) {

            var temp = this.form.questions[index];
            this.form.questions[index] = this.form.questions[index - 1];
            this.form.questions[index - 1] = temp;
            this.currentlySelectedIndex = index - 1;

        };

        this.shiftDown = function(stepIndex, index){

            var temp = this.form.questions[index];
            this.form.questions[index] = this.form.questions[index + 1];
            this.form.questions[index + 1] = temp;
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
            return index == (this.form.questions.length - 1);
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
            return this.form.questions.length == 1;
        };

        this.addOption = function (question) {
            if (!question.value) {
                question.value = [];
            }
            question.value.push({ prompt: "", value: 1 , valueWeight: 1 });
        };

        this.removeOption = function ( question, index) {
            if(question.value.length > 1) {
                question.value.splice(index, 1);
            }
        };

    }])
    .service('formsService', ['$http', 'RestUtil', 'userService', function($http, restUtil, userService) {

        /**
         * initializes basic template for a new form.
         *
         * @returns single step form with a single questions
         */
        this.initForm = function() {
            return {
                description: "",
                username: userService.getUsername(),
                disabled: false,
                questions: [
                    {
                        label:"",
                        type: "SHORT_RESPONSE",
                        weight: 1,
                        valueWeight: 1,
                        required: false
                    }
                ]
            }
        };

        this.getFormsByUsername = function(username) {

            this.$http = $http;

            var url = restUtil.URL + '/template/search/' + username;

            return this.$http.get(url);

        };

        this.getFormById = function(id) {

            this.$http = $http;

            var url = restUtil.URL + '/template/view/' + id;

            return this.$http.get(url);

        };

        this.deleteForm = function(id) {
            this.$http = $http;

            var url = restUtil.URL + '/template/' + id;

            return this.$http.delete(url);
        };

        /**
         * Post request to server in order to retrieve form data
         *
         * @returns {HttpPromise} - Promise that resolves to http response
         */
        this.getForm = function() {

            this.$http = $http;

            var url = restUtil.URL + '/template';

            return this.$http.get(url);
        };

        /**
         * Post request to server in order to authenticate the user
         *
         * @param username - the username
         * @param password - the password
         * @returns {HttpPromise} - Promise that resolves to http response
         */
        this.submitForm = function(form) {

            this.$http = $http;

            var url = restUtil.URL + '/template';
            var form = form;

            return this.$http.post(url, form);
        }
    }]);