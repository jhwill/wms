angular.module('app')
    .controller('OrderCtrl',
        /* @ngInject */
        function($scope, appService, appDialog, $timeout) {
            $scope.pager = {};
            $scope.itemModel = [];
            $scope.date = null;
            $scope.selected = [];
            $scope.itemSelected = [];

            function initData() {
                appService.ajaxGet('/api/core/order').then(function(d) {
                    $scope.date = d;
                });
                $scope.pageSearch(1);
            };

            $scope.pageSearch = function(page) {
                var url = '/api/core/order/search?page=' + (page - 1);
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

            $scope.closeDialog = function() {
                $mdSelect.hide();
                $scope.selectedMenuTemplate = null;
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
                    appService.ajaxDelete('/api/core/order/' + item.id).then(function() {
                        $scope.pager.content.splice($scope.pager.content.indexOf(item), 1)
                        appDialog.success();
                        $scope.pageSearch($scope.pager.currentPage);
                    });
                });
            };

            $scope.showItem = function (orderNumber) {
                var url = '/api/core/order/getOrderItemByOrderNumber?orderNumber='+orderNumber;
                appService.ajaxGet(url).then(function(d) {
                    $scope.itemModel = d;
                    $('#dialogItemForm').modal('show');
                });
            }
            var dialog = appDialog.getModal('dialogForm');
            $scope.openUserDialog = function(user) {
                angular.resetForm($scope.formUser);
                $scope.model = angular.copy(user) || {};
                $scope.model.__origin = user;
                dialog.show();
            };
            $scope.save = function() {
                $scope.saving = true;
                appService.ajaxSave('/api/core/order', $scope.formModel).then(function(res) {
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

            var updateSelected = function (action, id) {
                if (action == 'add' && $scope.selected.indexOf(id) == -1) $scope.selected.push(id);
                if (action == 'remove' && $scope.selected.indexOf(id) != -1) $scope.selected.splice($scope.selected.indexOf(id), 1);
            };
            //更新某一列数据的选择
            $scope.updateSelection = function ($event, id) {
                var checkbox = $event.target;
                var action = (checkbox.checked ? 'add' : 'remove');
                updateSelected(action, id);
            };
            //全选操作
            $scope.selectAll = function ($event,pager) {
                var checkbox = $event.target;
                var action = (checkbox.checked ? 'add' : 'remove');
                for (var i = 0; i < pager.content.length; i++) {
                    var contact = pager.content[i];
                    updateSelected(action, contact.id);
                }
            };
            $scope.isSelected = function (id) {
                return $scope.selected.indexOf(id) >= 0;
            };
            $scope.isSelectedAll = function (pager) {
                return $scope.selected.length === pager.content.length;
            };

            //orderItem
            var itemUpdateSelected = function (action, id) {
                if (action == 'add' && $scope.itemSelected.indexOf(id) == -1) $scope.itemSelected.push(id);
                if (action == 'remove' && $scope.itemSelected.indexOf(id) != -1) $scope.itemSelected.splice($scope.itemSelected.indexOf(id), 1);
            };
            //更新某一列数据的选择
            $scope.itemUpdateSelection = function ($event, id) {
                var checkbox = $event.target;
                var action = (checkbox.checked ? 'add' : 'remove');
                itemUpdateSelected(action, id);
            };
            //全选操作
            $scope.itemSelectAll = function ($event,itemModel) {
                var checkbox = $event.target;
                var action = (checkbox.checked ? 'add' : 'remove');
                for (var i = 0; i < itemModel.length; i++) {
                    var contact = itemModel[i];
                    itemUpdateSelected(action, contact.id);
                }
            };
            $scope.itemIsSelected = function (id) {
                return $scope.itemSelected.indexOf(id) >= 0;
            };
            $scope.itemIsSelectedAll = function (itemModel) {
                return $scope.itemSelected.length === itemModel.length;
            };

            $scope.printOrderItem = function () {
                console.error($scope.selected);
            }

            $scope.markAsOutOfStock = function () {
                console.error($scope.itemSelected);
                var ids = $scope.itemSelected
                appService.ajaxPost('/api/core/order/markAsOutOfStock', ids).then(function(res) {
                    appDialog.getModal('dialogItemForm').close();
                });
            }
        });
