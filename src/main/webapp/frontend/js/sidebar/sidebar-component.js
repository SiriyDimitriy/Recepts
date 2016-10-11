export default function Sidebar() {
    'ngInject';

    return {
        controller: SidebarController,
        controllerAs: 'sidebarCtrl',
        templateUrl: '/frontend/html/sidebar/sidebar.html',
        bindings: {
            state: '=',
            query: '=',
            sort: '=',
            itemsPerPage: '=',
            receptId: '='
        }
    };
}

export function SidebarController(stateService, contentService) {

    const vm = this;

    Object.assign(vm, {
        $onInit,
        createRecept,
        editIngridients,
        parseFile
    });

    function parseFile() {
        if (vm.file) {
            contentService.parseReceptFromXML(vm.file)
                .then((data)=> {
                    vm.file = null;
                    vm.receptId = data;
                    vm.state = stateService.states.RECEPT_DESCRIPTION;

                })
                .catch((reason) => vm.error = reason);
        }
    }

    function createRecept() {
        vm.state = stateService.states.RECEPT_CREATE;
    }

    function editIngridients() {
        vm.state = stateService.states.INGRIDIENT_EDIT;
    }

    function $onInit() {
    }
}