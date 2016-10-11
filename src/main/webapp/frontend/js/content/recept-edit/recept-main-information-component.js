export default function ReceptMainInformation() {
    'ngInject';

    return {
        controller: ReceptMainInformationController,
        controllerAs: 'receptMainInformationCtrl',
        templateUrl: '/frontend/html/content/edit/recept-main-information.html',
        bindings: {
            receptId: '=',
            model: '=',
            belowContentVisible: '='
        }
    };
}

export function ReceptMainInformationController(menuService,
                                                contentService) {

    const vm = this;

    Object.assign(vm, {
        $onInit,
        isSaveMainInfoVisible,
        saveMainInfo
    });

    function saveFile() {
        if (vm.foto) {
            contentService.saveReceptFile(vm.foto, vm.receptId)
                .then((data)=> {
                    vm.file = contentService.getUrlFromPngFile(data);
                    vm.foto = null;
                });
        }
    }

    function $onInit() {
        menuService.getDepartments()
            .then((data)=> {
                vm.departs = data;
            });
        if (vm.receptId) {
            contentService.getReceptFile(vm.receptId)
                .then((data)=> {
                    vm.file = contentService.getUrlFromPngFile(data);
                });
        }
    }

    function saveMainInfo() {
        if (vm.model.id) {
            contentService.getRecept(vm.model.id)
                .then((data)=> {
                    if (vm.model.name === data.name) {
                        contentService.saveRecept(vm.model)
                            .then((data)=> {
                                vm.belowContentVisible = true;
                                vm.receptId = data.data;
                                vm.error = null;
                                saveFile();
                            });
                    } else {
                        saveUniqueRecept();
                    }
                });

        } else {
            saveUniqueRecept();
        }

    }

    function saveUniqueRecept() {
        contentService.saveUniqueRecept(vm.model)
            .then((data)=> {
                if (!data.data) {
                    vm.error = 'Такой рецепт уже есть';
                } else {
                    vm.belowContentVisible = true;
                    vm.receptId = data.data;
                    vm.error = null;
                    saveFile();
                }
            });
    }

    function isSaveMainInfoVisible() {
        if (vm.model.name && vm.model.departId.id) {
            return true;
        }
        return false;
    }

}
