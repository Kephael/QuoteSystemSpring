'use strict';

angular.module('myApp.restMock', ['ngMockE2E', 'myApp.common'])/**/

//MOCKS
    .run(function($httpBackend){

        console.log("$httpBackend", $httpBackend);

        // //Mock Backend
        $httpBackend.whenPOST("http://localhost:8080/authenticate").respond(
            {
                roles: ['ROLE_ADMIN', 'ROLE_USER']
            }
        );

        $httpBackend.whenPOST("http://localhost:8080/authenticate").respond(
            {
                roles: ['ROLE_ADMIN', 'ROLE_USER']
            }
        );

        $httpBackend.whenGET("http://localhost:8080/template/955006").respond(
            {
                "identity":955006,
                "description": "Test Template",
                "username":"User",
                "questions":[
                    {
                        "prompt": "Sample Radio Group",
                        "value":[
                            {
                                "prompt": "Test option 1",
                                "value":500.0,
                                "valueWeight":1.0,
                                "type":"BOOLEAN",
                                "response":true,
                                "required":false
                            },
                            {
                                "prompt": "Test option 2",
                                "value": 250.0,
                                "valueWeight": 2.0,
                                "type":"BOOLEAN",
                                "response":true,
                                "required":false
                            }
                        ],


                        "valueWeight":0.0,
                        "type":"RADIO",
                        "response":0,
                        "required":false
                    },
                    {
                        "prompt":"This is a test prompt, can you see this?",
                        "value":null,
                        "valueWeight":0.0,
                        "type":"LONG_RESPONSE",
                        "response":"Yes, I can see this.",
                        "required":true
                    }
                ],
                "totalQuoteValue":500.0
            },
            {
                "identity":955006,
                "description": "another test template",
                "username":"junit",
                "questions":[
                    {
                        "prompt":null,
                        "value":[
                            {
                                "prompt":null,
                                "value":500.0,
                                "valueWeight":1.0,
                                "type":"BOOLEAN",
                                "response":true,
                                "required":false
                            }
                        ],
                        "valueWeight":0.0,
                        "type":"RADIO",
                        "response":0,
                        "required":false
                    },
                    {
                        "prompt":"This is a test prompt, can you see this?",
                        "value":null,
                        "valueWeight":0.0,
                        "type":"LONG_RESPONSE",
                        "response":"Yes, I can see this.",
                        "required":true
                    }
                ],
                "totalQuoteValue":500.0
            }
        );

        $httpBackend.whenGET("http://localhost:8080/template/search/undefined").respond([
            {
                "identity":955006,
                "description": "Test Template",
                "username":"User",
                "questions":[
                    {
                        "prompt": "Sample Radio Group",
                        "value":[
                            {
                                "prompt": "Test option 1",
                                "value":500.0,
                                "valueWeight":1.0,
                                "type":"BOOLEAN",
                                "response":true,
                                "required":false
                            },
                            {
                                "prompt": "Test option 2",
                                "value": 250.0,
                                "valueWeight": 2.0,
                                "type":"BOOLEAN",
                                "response":true,
                                "required":false
                            }
                        ],


                        "valueWeight":0.0,
                        "type":"RADIO",
                        "response":0,
                        "required":false
                    },
                    {
                        "prompt":"This is a test prompt, can you see this?",
                        "value":null,
                        "valueWeight":0.0,
                        "type":"LONG_RESPONSE",
                        "response":"Yes, I can see this.",
                        "required":true
                    }
                ],
                "totalQuoteValue":500.0
            },
            {
                "identity":955006,
                "description": "Another Test Template",
                "username":"junit",
                "questions":[
                    {
                        "prompt":null,
                        "value":[
                            {
                                "prompt":null,
                                "value":500.0,
                                "valueWeight":1.0,
                                "type":"BOOLEAN",
                                "response":true,
                                "required":false
                            }
                        ],
                        "valueWeight":0.0,
                        "type":"RADIO",
                        "response":0,
                        "required":false
                    },
                    {
                        "prompt":"This is a test prompt, can you see this?",
                        "value":null,
                        "valueWeight":0.0,
                        "type":"LONG_RESPONSE",
                        "response":"Yes, I can see this.",
                        "required":true
                    }
                ],
                "totalQuoteValue":500.0
            },
            {
                "identity":955006,
                "description": "Test Template 2",
                "username":"User",
                "questions":[
                    {
                        "prompt": "Sample Radio Group",
                        "value":[
                            {
                                "prompt": "Test option 1",
                                "value":500.0,
                                "valueWeight":1.0,
                                "type":"BOOLEAN",
                                "response":true,
                                "required":false
                            },
                            {
                                "prompt": "Test option 2",
                                "value": 250.0,
                                "valueWeight": 2.0,
                                "type":"BOOLEAN",
                                "response":true,
                                "required":false
                            }
                        ],


                        "valueWeight":0.0,
                        "type":"RADIO",
                        "response":0,
                        "required":false
                    },
                    {
                        "prompt":"This is a test prompt, can you see this?",
                        "value":null,
                        "valueWeight":0.0,
                        "type":"LONG_RESPONSE",
                        "response":"Yes, I can see this.",
                        "required":true
                    }
                ],
                "totalQuoteValue":500.0
            },
            {
                "identity":955006,
                "description": "Test Template the Fourth",
                "username":"junit",
                "questions":[
                    {
                        "prompt":null,
                        "value":[
                            {
                                "prompt":null,
                                "value":500.0,
                                "valueWeight":1.0,
                                "type":"BOOLEAN",
                                "response":true,
                                "required":false
                            }
                        ],
                        "valueWeight":0.0,
                        "type":"RADIO",
                        "response":0,
                        "required":false
                    },
                    {
                        "prompt":"This is a test prompt, can you see this?",
                        "value":null,
                        "valueWeight":0.0,
                        "type":"LONG_RESPONSE",
                        "response":"Yes, I can see this.",
                        "required":true
                    }
                ],
                "totalQuoteValue":500.0
            }
            ]
        );



        // //form response
        // $httpBackend.whenGET("http://localhost:8000/template").respond({
        //     steps: [
        //         {
        //             label: "Step 1",
        //             complete: false,
        //             disabled:false,
        //             questions: [
        //                 {
        //                     label: "How to question 1?",
        //                     type: "Text Input",
        //                     position: 1,
        //                     cellValue: "A1",
        //                     value: null
        //                 },
        //                 {
        //                     label: "How to question 2?",
        //                     type: "Radio Buttons",
        //                     selected: "option 1",
        //                     options: [
        //                       'options 1',
        //                       'option 2',
        //                     ],
        //                     position: 2,
        //                     cellValue: "A2",
        //                     value: null
        //                 },
        //                 {
        //                     label: "How to question 3?",
        //                     type: "Text Input",
        //                     position: 3,
        //                     cellValue: "A3",
        //                     value: null
        //                 },
        //                 {
        //                     label: "How to question 4?",
        //                     type: "Text Input",
        //                     position: 4,
        //                     cellValue: "A4",
        //                     value: null
        //                 }
        //             ]
        //         },
        //         {
        //             label: "Step 2",
        //             complete: false,
        //             disabled:true,
        //             questions: [
        //                 {
        //                     label: "How to question 3?",
        //                     type: "Text Input",
        //                     position: 1,
        //                     cellValue: "A3",
        //                     value: null
        //                 },
        //                 {
        //                     label: "How to question 4?",
        //                     type: "Text Input",
        //                     position: 2,
        //                     cellValue: "A4",
        //                     value: null
        //                 }
        //             ]
        //         }
        //     ]
        // });

        // $httpBackend.whenPOST("http://localhost:8000/authenticate").passThrough();
        $httpBackend.whenGET(/[\s\S]*/).passThrough();
    });