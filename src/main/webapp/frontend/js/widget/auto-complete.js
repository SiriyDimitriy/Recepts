export default function Autocomplete($parse) {
    return {
        restrict: 'A',
        link: function (scope, element, attrs) {
            element[0].easyAutocomplete({
                data: $parse(attrs.autoComplete),
                theme: 'dark'
            });
        }
    };
}
