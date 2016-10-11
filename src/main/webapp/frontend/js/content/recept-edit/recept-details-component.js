export default function ReceptDetails() {
    'ngInject';

    return {
        controller: ReceptDetailsController,
        controllerAs: 'receptDetailsCtrl',
        templateUrl: '/frontend/html/content/edit/recept-details.html',
        bindings: {
            receptId: '<',
            model: '='
        }
    };
}

export function ReceptDetailsController(contentService) {

    const vm = this;

    Object.assign(vm, {
        $onInit,
        saveDetail,
        deleteDetail,
        updateDetail,

        description: null
    });

    function $onInit() {
        setDetailsFromServer();
    }

    function saveDetail() {
        contentService.saveDetail(vm.detail, vm.receptId)
            .then((id)=> {
                saveFile(id)
                    .then((data)=> {
                        setDetailsFromServer();
                    });
                vm.detail.description = null;
            });
    }

    function saveFile(detailId) {
        if (vm.foto) {
            return contentService.saveDetailFile(vm.foto, detailId)
                .then((data)=> {
                    vm.foto = null;
                });
        }
        return new Promise(()=> {
        });
    }

    function setDetailsFromServer() {
        contentService.getReceptsDetails(vm.receptId)
            .then((data)=> {
                vm.model.details = data;
                vm.model.details.forEach((detail)=> {
                    contentService.getDetailFile(detail.id)
                        .then((data)=> {
                            detail.file = contentService.getUrlFromPngFile(data);
                        });
                });
            });
    }

    function deleteDetail(detId) {
        contentService.deleteDetail(detId)
            .then((data)=> {
                setDetailsFromServer();
            });
    }

    function updateDetail(detId) {
        vm.model.details.forEach((detail)=> {
            if (detail.id === detId) {
                contentService.saveDetail(detail, vm.receptId)
                    .then((id)=> {
                        setDetailsFromServer();
                    });
                return;
            }
        });
    }

}

