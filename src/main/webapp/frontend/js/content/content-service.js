export default function ContentService($http,
                                       $q) {
    'ngInject';

    Object.assign(this, {
        getReceptList,
        getReceptListByTag,
        getRecept,
        deleteRecept,
        saveRecept,
        saveUniqueRecept,
        getIngridients,
        saveProportion,
        getReceptsProportions,
        deleteProportion,
        saveDetail,
        getReceptsDetails,
        deleteDetail,
        saveReference,
        getReceptsReferences,
        deleteReference,
        saveReceptsTag,
        getReceptsTags,
        deleteTagFromRecept,
        saveIngridient,
        deleteIngridient,
        saveReceptFile,
        getReceptFile,
        getUrlFromPngFile,
        getDetailFile,
        saveDetailFile,
        massiveGrouping,
        parseReceptFromXML,
        getPdfFromRecept
    });

    function getReceptList(departId) {
        return $http.get('recept_list.req', {
                params: {
                    departId: departId
                }
            })
            .then((response) => response.data)
            .catch((response) => $q.reject(response.data));
    }

    function getReceptListByTag(tagId) {
        return $http.get('recept_list_tag.req', {
                params: {
                    tagId: tagId
                }
            })
            .then((response) => response.data)
            .catch((response) => $q.reject(response.data));
    }

    function getRecept(receptId) {
        return $http.get('recept.req', {
                params: {
                    receptId: receptId
                }
            })
            .then((response) => response.data)
            .catch((response) => $q.reject(response.data));
    }

    function deleteRecept(receptId) {
        return $http.delete('recept.req', {
            params: {
                receptId: receptId
            }
        });
    }

    function saveRecept(recept) {
        return $http.post('recept.req', recept);
    }

    function saveUniqueRecept(recept) {
        return $http.post('recept_unique.req', recept);
    }

    function getIngridients() {
        return $http.get('ing_list.req')
            .then((response) => response.data)
            .catch((response) => $q.reject(response.data));
    }

    function saveProportion(proportion, receptId) {
        return $http.post('proportion.req', proportion, {
                params: {
                    receptId: receptId
                }
            })
            .then((response) => response.data)
            .catch((response) => $q.reject(response.data));
    }

    function getReceptsProportions(receptId) {
        return $http.get('proportions.req', {
                params: {
                    receptId: receptId
                }
            })
            .then((response) => response.data)
            .catch((response) => $q.reject(response.data));
    }

    function deleteProportion(propId) {
        return $http.delete('proportion.req', {
            params: {
                propId: propId
            }
        });
    }

    function saveDetail(detail, receptId) {
        detail.receptId = receptId;
        return $http.post('detail.req', detail)
            .then((response) => response.data)
            .catch((response) => $q.reject(response.data));
    }

    function getReceptsDetails(receptId) {
        return $http.get('details.req', {
                params: {
                    receptId: receptId
                }
            })
            .then((response) => response.data)
            .catch((response) => $q.reject(response.data));
    }

    function deleteDetail(detId) {
        return $http.delete('detail.req', {
            params: {
                detId: detId
            }
        });
    }

    function saveReference(reference, receptId) {
        return $http.post('reference.req', reference, {
                params: {
                    receptId: receptId
                }
            })
            .then((response) => response.data)
            .catch((response) => $q.reject(response.data));
    }

    function getReceptsReferences(receptId) {
        return $http.get('references.req', {
                params: {
                    receptId: receptId
                }
            })
            .then((response) => response.data)
            .catch((response) => $q.reject(response.data));
    }

    function deleteReference(refId) {
        return $http.delete('reference.req', {
            params: {
                refId: refId
            }
        });
    }

    function saveReceptsTag(tagId, receptId) {
        return $http.get('category_save.req', {
                params: {
                    receptId: receptId,
                    tagId: tagId
                }
            })
            .then((response) => response.data)
            .catch((response) => $q.reject(response.data));
    }

    function getReceptsTags(receptId) {
        return $http.get('recept_tags.req', {
                params: {
                    receptId: receptId
                }
            })
            .then((response) => response.data)
            .catch((response) => $q.reject(response.data));
    }

    function deleteTagFromRecept(tagId, receptId) {
        return $http.get('category_del.req', {
            params: {
                receptId: receptId,
                tagId: tagId
            }
        });
    }

    function saveIngridient(ing) {
        return $http.post('ingridient.req', ing);
    }

    function deleteIngridient(ingId) {
        return $http.delete('ingridient.req', {
            params: {
                ingId: ingId
            }
        });
    }

    function parseReceptFromXML(file) {
        let fd = new FormData();
        fd.append('file', file);
        return $http.post('xmlFile.req', fd, {
                transformRequest: angular.identity,
                headers: {'Content-Type': undefined}
            })
            .then((response) => response.data)
            .catch((response) => $q.reject(response.data));
    }

    function getPdfFromRecept(receptId) {
        return $http.get('pdfFile.req', {
            params: {
                receptId: receptId
            },
            responseType: 'arraybuffer'
        });
    }

    function saveReceptFile(file, receptId) {
        let fd = new FormData();
        fd.append('file', file);
        fd.append('receptId', receptId);
        return $http.post('file.req', fd, {
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined},
            responseType: 'arraybuffer'
        });
    }

    function getReceptFile(receptId) {
        return $http.get('file.req', {
            params: {
                receptId: receptId
            },
            responseType: 'arraybuffer'
        });
    }

    function saveDetailFile(file, detailId) {
        var fd = new FormData();
        fd.append('file', file);
        fd.append('detailId', detailId);
        return $http.post('detail_file.req', fd, {
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined},
            responseType: 'arraybuffer'
        });
    }

    function getDetailFile(detailId) {
        return $http.get('detail_file.req', {
            params: {
                detailId: detailId
            },
            responseType: 'arraybuffer'
        });
    }

    function getUrlFromPngFile(data) {
        if (data.data) {
            let blob = new Blob([data.data], {type: "image/png"});
            let urlCreator = window.URL || window.webkitURL;
            return urlCreator.createObjectURL(blob);
        }
        return null;
    }

    function massiveGrouping(n, inputArray, arr) {
        let inputArr = angular.copy(inputArray);
        arr = arr || [];
        if (inputArr.length != 0) {
            let emptyArr = [];
            for (let i = 0; i < n; i++) {
                if (inputArr[i]) {
                    emptyArr.push(inputArr[i]);
                }
            }
            inputArr.splice(0, emptyArr.length);
            arr.push(emptyArr);
            return massiveGrouping(n, inputArr, arr);
        } else {
            return arr;
        }
    }

}