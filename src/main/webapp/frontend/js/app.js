import $ from 'jquery';

import 'easy-autocomplete';
import angular from 'angular'
//import 'jquery-ui-bundle';
//import 'jquery-ui';
//import 'jquery-ui/ui/widgets/autocomplete';
import 'babel-polyfill';
import MenuModule from './menu/menu-module';
import SidebarModule from './sidebar/sidebar-module';
import ContentModule from './content/content-module';
import WidgetModule from './widget/widget-module';
import Main from './main-component';
import ReceptModel from './recept-model';
import StateService from './state-service';

/* App Module */

const MODULE_NAME = 'app';

const receptApp = angular.module(MODULE_NAME, [
    WidgetModule,
    MenuModule,
    ContentModule,
    SidebarModule
]);

receptApp.controller('headController', function($scope) {
    $scope.theme = {theme: 'fire'};
});

receptApp.component('main', new Main());

receptApp.service('receptModel', ReceptModel);

receptApp.service('stateService', StateService);

receptApp.config(['$compileProvider',
    function ($compileProvider) {
        $compileProvider.aHrefSanitizationWhitelist(/^\s*(https?|ftp|mailto|tel|file|blob):/);
    }]);

export {receptApp, MODULE_NAME as default};