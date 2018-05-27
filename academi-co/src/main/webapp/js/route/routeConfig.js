/* 
    We configure here the URL name for the user:
    We define here:
        - the path (including pathparam)
        - the Angular Controller used for each page
*/
'use strict';


/* 
    This component is included in academi-co angular module
    This component uses ngRoute for routing
*/
var route = angular.module('route', ['ngRoute']);


/*
    This is to change the title at every load instead of putting title in partial template
    (which is wrong and give warning because it loads the page before putting title)
*/
route.run(['$rootScope', '$route', function($rootScope, $route) {
    $rootScope.$on('$routeChangeSuccess', function() {
        document.title = $route.current.title;
    });
}]);

/* Configuration of the routes */
route.config(["$routeProvider", "$locationProvider", function($routeProvider, $locationProvider) {

    /* We change the hashPrefix to '' to remove the dash in the URL */
    // $locationProvider.hashPrefix('');
    /* 
    How to remove the hash from the URL:
    -   uncomment the following line
    -   uncomment in header the base tag 
    -   in all href, remove the hash

    pros: nice URL for client 
    cons: can't access directly to a certain URL without passing from home page
    For more details:
    AngularJS documentation:
    https://docs.angularjs.org/guide/$location
    https://stackoverflow.com/questions/17646843/angularjs-html5-mode-how-do-direct-links-work-without-server-specific-changes
    */
    // $locationProvider.html5Mode(true);


    /*
        How basically it works:
            -   the title of the page 
            -   the when('/link') --> define the link '/link'
            -   the templateUrl   --> it is the main Content that will be injected in the main index.html with ng-view
            -   the controller    --> We define a controller in allPageControllers that we do all the request part to the back-end
                                        and display part
        
        The usage of :XX, is defining a parameters in the URL that can be used in the controller.
        We can retrieve with pathparam
    */
    $routeProvider
        .when('/admin', {
            title       : 'Administrator - Academi-co',
            templateUrl : 'pages/administration.html',
            controller  : 'adminController'
        })
        .when('/forbidden', {
            title       : 'Forbidden - Academi-co',
            templateUrl : 'pages/forbidden.html',
            controller  : 'forbiddenController'
        })
        .when('/help', {
            title       : 'Help - Academi-co',
            templateUrl : 'pages/help.html',
            controller  : 'helpController'
        })
        .when('/internalServerError', {
            title       : 'Internal Server Error - Academi-co',
            templateUrl : 'pages/internalServerError.html',
            controller  : 'internalServerErrorController'
        })
        .when('/login', {
            title       : 'Login - Academi-co',
            templateUrl : 'pages/login.html',
            controller  : 'loginController'
        })
        .when('/loginError', {
            title       : 'Error Login- Academi-co',
            templateUrl : 'pages/loginError.html',
            controller  : 'loginErrorController'
        })
        .when('/moderation', {
            title       : 'Moderator - Academi-co',
            templateUrl : 'pages/moderation.html',
            controller  : 'moderationController'
        })
        .when('/notFound', {
            title       : '404 Not Found - Academi-co',
            templateUrl : 'pages/notFound.html',
            controller  : 'notFoundController'
        })
        .when('/newThread', {
            title       : 'New Thread - Academi-co',
            templateUrl : 'pages/postThread.html',
            controller  : 'postThreadController'
        })
        .when('/profile/:id', {
        // .when('/profile?user=:id', {
            title       : 'Profile - Academi-co',
            templateUrl : 'pages/profile.html',
            controller  : 'profileController'
        })
        .when('/result', {
            title       : 'Search - Academi-co',
            templateUrl : 'pages/result.html',
            controller  : 'resultController'
        })
        .when('/settings', {
            title       : 'Settings - Academi-co',
            templateUrl : 'pages/settingsPreferences.html',
            controller  : 'settingsPreferencesController'
        })
        .when('/signUp', {
            title       : 'Sign Up- Academi-co',
            templateUrl : 'pages/signUp.html',
            controller  : 'signUpController'
        })
        .when('/threads/:id', {
        // .when('/threads?id=:id', {
            title       : 'Thread - Academi-co',
            templateUrl : 'pages/thread.html',
            controller  : 'threadController'
        })
        .when('/preview', {
            title       : 'Preview - Academi-co',
            templateUrl : 'pages/preview.html',
            controller  : 'previewController'
        })
        
        // route for the home page
        .when('/', {
            title       : 'Home - Academi-co',
            templateUrl : 'pages/home.html',
            controller  : 'homeController'
        })
        .otherwise({
            redirectTo: '/preview'
            });

        
}]);





   

