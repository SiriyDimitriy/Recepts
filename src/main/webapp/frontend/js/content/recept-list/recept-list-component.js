export default function ReceptList() {
    'ngInject';

    return {
        controller: ReceptListController,
        controllerAs: 'receptListCtrl',
        templateUrl: '/frontend/html/content/recept-list.html',
        bindings: {
            choosenDepartId: '<',
            choosenTagId: '<',
            state: '=',
            receptId: '=',
            query: '<',
            sort: '<',
            itemsPerPage: '<'
        }
    };
}

export function ReceptListController(contentService, stateService) {

    const vm = this;

    Object.assign(vm, {
        $onChanges,
        showRecept,
        showPage,

        pageNumber: 0
    });

    function $onChanges({choosenDepartId, choosenTagId, itemsPerPage}) {
        if (itemsPerPage && itemsPerPage.currentValue && vm.groupingReceptList) {
            vm.groupingReceptList = contentService.massiveGrouping(itemsPerPage.currentValue, vm.receptList);
            showPage(vm.pageNumber);
            return;
        }
        if (choosenDepartId && choosenDepartId.currentValue) {
            contentService.getReceptList(choosenDepartId.currentValue)
                .then(formingReceptList);
            return;
        }
        if (choosenTagId && choosenTagId.currentValue) {
            contentService.getReceptListByTag(choosenTagId.currentValue)
                .then(formingReceptList);
        }
    }

    function formingReceptList(data) {
        vm.receptList = data;
        vm.pageNumber = 0;
        vm.groupingReceptList = contentService.massiveGrouping(vm.itemsPerPage, vm.receptList);
        showPage(0);
    }

    function showRecept(receptId) {
        vm.state = stateService.states.RECEPT_DESCRIPTION;
        vm.receptId = receptId;
    }

    function showPage(index) {
        vm.pageNumber = index;
        vm.groupingReceptList[index].forEach((recept)=> {
            return contentService.getReceptFile(recept.id)
                .then((data)=> {
                    recept.file = contentService.getUrlFromPngFile(data);
                });
        });
    }
}