import Content from './content-component';
import ContentService from './content-service';
import ReceptList from './recept-list/recept-list-component';
import ReceptDescription from './recept-description/recept-description-component';
import Ingridients from './ingridients/ingridients-component';
import ReceptEditModule from './recept-edit/recept-edit-module';

const MODULE_NAME = 'content';

const module = angular.module(MODULE_NAME, [ReceptEditModule]);

module
    .component('receptContent', new Content())
    .service('contentService', ContentService)
    .component('receptList', new ReceptList())
    .component('receptDescription', new ReceptDescription())
    .component('ingridients', new Ingridients());

export {module, MODULE_NAME as default};
