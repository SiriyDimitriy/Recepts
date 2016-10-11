export default function ReceptProportions() {
    'ngInject';

    return {
        controller: ReceptProportionsController,
        controllerAs: 'receptProportionsCtrl',
        templateUrl: '/frontend/html/content/edit/recept-proportions.html',
        bindings: {
            receptId: '<',
            model: '='
        }
    };
}

export function ReceptProportionsController(contentService) {

    const vm = this;

    Object.assign(vm, {
        $onInit,
        saveProportion,
        updateProportion,
        deleteProportion,

        proportion: null
    });

    function setEmptyProportion() {
        vm.proportion = {
            norma: null,
            ingridient: {
                id: null,
                name: null
            }
        }
    }

    function deleteProportion(propId) {
        contentService.deleteProportion(propId)
            .then((data)=> {
                setProportionsFromServer();
            });
    }

    function updateProportion(propId) {
        vm.model.proportions.forEach((prop)=> {
            if (prop.id === propId) {
                contentService.saveProportion(prop, vm.receptId)
                    .then((id)=> {
                        setProportionsFromServer();
                    });
                return;
            }
        });
    }

    function saveProportion() {
        contentService.saveProportion(vm.proportion, vm.receptId)
            .then((id)=> {
                setEmptyProportion();
                setProportionsFromServer();
            });
    }

    function $onInit() {
        setEmptyProportion();
        contentService.getIngridients()
            .then((data)=> {
                vm.ingridients = data;
            })
    }

    function setProportionsFromServer() {
        contentService.getReceptsProportions(vm.receptId)
            .then((data)=> {
                vm.model.proportions = data;
            });
    }

}
