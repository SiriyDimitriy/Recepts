export default function MenuService(
    $http,
    $q
) {
    'ngInject';

    Object.assign(this, {
        getDepartments,
        getTags
    });

    function getDepartments() {
        return $http.get('departs.req')
            .then((response) => response.data)
            .catch((response) => $q.reject(response.data));
    }

    function getTags() {
        return $http.get('tags.req')
            .then((response) => response.data)
            .catch((response) => $q.reject(response.data));
    }
}

