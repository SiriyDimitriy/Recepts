export default function ReceptReferences() {
    'ngInject';

    return {
        controller: ReceptReferencesController,
        controllerAs: 'receptReferencesCtrl',
        templateUrl: '/frontend/html/content/edit/recept-references.html',
        bindings: {
            receptId: '<',
            model: '=',
        }
    };
}

export function ReceptReferencesController(contentService) {

    const vm = this;

    Object.assign(vm, {
        $onInit,
        deleteReference,
        saveReference,

        reference: {
            receptReferenceId: null
        }
    });

    function $onInit() {
        contentService.getReceptList(-1)
            .then((data)=> {
                vm.recepts = data;
            });
    }

    function saveReference() {
        contentService.saveReference(vm.reference, vm.receptId)
            .then((id)=> {
                vm.reference = {
                    receptReferenceId: null
                };
                setReferencesFromServer();
            });
    }

    function setReferencesFromServer() {
        contentService.getReceptsReferences(vm.receptId)
            .then((data)=> {
                vm.model.references = data;
            });
    }

    function deleteReference(refId) {
        contentService.deleteReference(refId)
            .then((data)=> {
                setReferencesFromServer();
            });
    }

}