export default function ReceptDescription() {
    'ngInject';

    return {
        controller: ReceptDescriptionController,
        controllerAs: 'receptDescriptionCtrl',
        templateUrl: '/frontend/html/content/recept-description.html',
        bindings: {
            receptId: '=',
            state: '='
        }
    };
}

export function ReceptDescriptionController(contentService,
                                            stateService) {

    const vm = this;

    Object.assign(vm, {
        $onInit,
        editRecept,
        deleteRecept,
        viewReference,
        getPdfFromRecept
    });

    function $onInit() {
        getReceptInformation();
    }

    function getFileData() {
        contentService.getReceptFile(vm.receptId)
            .then((data)=> {
                vm.recept.file = contentService.getUrlFromPngFile(data);
            });
        vm.recept.details.forEach((detail)=> {
            contentService.getDetailFile(detail.id)
                .then((data)=> {
                    detail.file = contentService.getUrlFromPngFile(data);
                });
        });
    }

    function getPdfFromRecept() {
        contentService.getPdfFromRecept(vm.receptId)
            .then((data)=> {
                vm.pdf = URL.createObjectURL(new Blob([data.data]));
            });
    }

    function getReceptInformation() {
        if (vm.receptId) {
            contentService.getRecept(vm.receptId)
                .then((data) => {
                    vm.recept = data;
                    getFileData();
                });
        }
    }

    function editRecept() {
        vm.state = stateService.states.RECEPT_EDIT;
    }

    function deleteRecept() {
        contentService.deleteRecept(vm.recept.id);
        vm.state = stateService.states.RECEPT_LIST;
    }

    function viewReference(receptId) {
        vm.receptId = receptId;
        getReceptInformation();
    }
}