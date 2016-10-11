import ConvertToNumber from './convert-to-number';
import FileModel from './file-model';
import Autocomplete from './auto-complete';

const MODULE_NAME = 'widget';

const module = angular.module(MODULE_NAME, []);

module
    .directive('convertToNumber', ConvertToNumber)
    .directive('fileModel', FileModel)
    .directive('autoComplete', Autocomplete);

export {module, MODULE_NAME as default};
