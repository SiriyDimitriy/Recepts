export default function ReceptTags() {
    'ngInject';

    return {
        controller: ReceptTagsController,
        controllerAs: 'receptTagsCtrl',
        templateUrl: '/frontend/html/content/edit/recept-tags.html',
        bindings: {
            receptId: '<',
            model: '=',
        }
    };
}

export function ReceptTagsController(contentService, menuService) {

    const vm = this;

    Object.assign(vm, {
        $onInit,
        saveTag,
        deleteTag,

        tagId: null
    });

    function $onInit() {
        menuService.getTags()
            .then((data)=> {
                vm.tags = data;
            });
    }

    function saveTag() {
        contentService.saveReceptsTag(vm.tagId, vm.receptId)
            .then((data)=> {
                vm.tagId = null;
                setTagsFromServer();
            });
    }

    function setTagsFromServer() {
        contentService.getReceptsTags(vm.receptId)
            .then((data)=> {
                vm.model.tags = data;
            });
    }

    function deleteTag(tagId) {
        contentService.deleteTagFromRecept(tagId, vm.receptId)
            .then((data)=> {
                setTagsFromServer();
            });
    }

}
