angular.module('app')
    .controller('StockCtrl',
        /* @ngInject */
        function($scope, appService, appDialog, $timeout) {
            $scope.pager = {};
            $scope.date = null;

            function initData() {
                appService.ajaxGet('/api/core/stock').then(function(d) {
                    $scope.date = d;
                });
                $scope.pageSearch(1);
            };

            $scope.pageSearch = function(page) {
                var url = '/api/core/stock/search?page=' + (page - 1);
                if ($scope.searchKeywords) {
                    url += '&keywords=' + $scope.searchKeywords;
                }
                appService.ajaxGet(url).then(function(d) {
                    $scope.pager = d;

                    if ($scope.searchKeywords) {
                        $timeout(function() {
                            $('table > tbody > tr > td, table > tbody > tr > td > span').highlightText($scope.searchKeywords);
                        }, 100);
                    }
                });
            };

            $scope.add = function(parent) {
                angular.resetForm($scope.form);
                if (parent) {
                    $scope.formModel = { parentId: parent.id, visible: true };
                } else {
                    $scope.formModel = { visible: true };
                }
                $('#dialogForm').modal('show');
            };
            $scope.remove = function(item) {
                appDialog.confirmDeletion(function(){
                    appService.ajaxDelete('/api/core/stock/' + item.id).then(function() {
                        $scope.pager.content.splice($scope.pager.content.indexOf(item), 1)
                        appDialog.success();
                        $scope.pageSearch($scope.pager.currentPage);
                    });
                });
            };

            var dialog = appDialog.getModal('dialogForm');
            $scope.openUserDialog = function(user) {
                angular.resetForm($scope.formUser);
                $scope.model = angular.copy(user) || {};
                $scope.model.__origin = user;
                dialog.show();
            };
            $scope.save = function() {
                $scope.saving = true;
                appService.ajaxSave('/api/core/stock', $scope.formModel).then(function(res) {
                    $scope.pageSearch(1);
                    dialog.close();
                }).finally(function() {
                    $scope.saving = false;
                });
            };

            $scope.search = function(keywords) {
                $scope.searchKeywords = keywords;
                $scope.pageSearch(1);
            };

            $scope.cancelSearch = function() {
                $scope.searchKeywords = null;
                $scope.pageSearch(1);
            };

            initData();
        });
