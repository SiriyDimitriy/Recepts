import Sidebar from './sidebar-component';

const MODULE_NAME = 'sidebar';

const module = angular.module(MODULE_NAME, []);

module
    .component('receptSidebar', new Sidebar());

export {module, MODULE_NAME as default};
