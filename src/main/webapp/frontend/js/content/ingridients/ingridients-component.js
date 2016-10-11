export default function Ingridients() {
    'ngInject';

    return {
        controller: IngridientsController,
        controllerAs: 'ingridientsCtrl',
        templateUrl: '/frontend/html/content/ingridients.html',
        bindings: {
            state: '='
        }
    };
}

export function IngridientsController(contentService) {

    const vm = this;

    Object.assign(vm, {
        $onInit,
        deleteIngridient,
        saveIngridient,

        ingridient: {
            name: null
        },
        nnn: [
            'dfasdfsdf',
            'fffffffffff',
            'aaaaaaaaa'
        ]
    });

    function deleteIngridient() {
        if (vm.ingridientId) {
            contentService.deleteIngridient(vm.ingridientId)
                .then(setDataFromServer);
        }
    }

    function saveIngridient() {
        if (vm.ingridient.name) {
            contentService.saveIngridient(vm.ingridient)
                .then((data)=> {
                    if (!data.data) {
                        vm.error = 'Такой ингридиет уже есть';
                    }
                    setDataFromServer();
                    vm.ingridient = {
                        name: null
                    }
                })
                .catch((response) => {
                    vm.error = response.data;
                });
        }
    }

    function $onInit() {
        setDataFromServer();
    }

    function setDataFromServer() {
        contentService.getIngridients()
            .then((data) => {
                vm.ingridients = data;
            });
    }

}
