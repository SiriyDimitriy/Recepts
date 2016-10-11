export default function Menu() {
    'ngInject';

    return {
        controller: MenuController,
        controllerAs: 'menuCtrl',
        templateUrl: '/frontend/html/menu/menu.html',
        bindings: {
            theme: '=',
            choosenDepartId: '=',
            choosenTagId: '=',
            state: '='
        }
    };
}

export function MenuController(menuService, stateService) {

    const vm = this;

    Object.assign(vm, {
        $onInit,
        chooseDepart,
        chooseTag,
        departs: []
    });

    function $onInit() {
        menuService.getDepartments()
            .then((data) => {
                vm.departs = data;
            });
        menuService.getTags()
            .then((data)=> {
                vm.tags = data;
            });
    }

    function chooseDepart(id) {
        vm.state = stateService.states.RECEPT_LIST;
        vm.choosenDepartId = id;
    }

    function chooseTag(id) {
        vm.state = stateService.states.RECEPT_LIST;
        vm.choosenTagId = id;
    }

}