import { Component, OnInit } from '@angular/core';
import {SelectItem} from 'primeng/api';
import {KeysService} from '../keys.service';

@Component({
  selector: 'app-eviction',
  templateUrl: './eviction.component.html'
})
export class EvictionComponent implements OnInit {
    public data: any[] = [];
    public filterQuery = '';
    public rowsOnPage = 10;
    public sortBy = '';
    public sortOrder = 'asc';
    public beanNames: SelectItem[];
    public mapNames: SelectItem[];
    public selectedKeysForEviction: any[] = [];
    public keyTermForEviction: String = '';


    constructor(private keysService: KeysService) { }

    ngOnInit() {
        this.loadData();
    }

    loadData() {
        this.keysService.getKeys().subscribe(response => {
            this.data = response;
            this.populateBeanNamesFilter(this.data);
            this.populateMapNamesFilter(this.data);
        });
    }
    populateMapNamesFilter(data) {
        this.mapNames = [];
        this.mapNames.push({label: 'All Maps', value: null});

        data.forEach((k) => {
            const beanItem: SelectItem = {label: k.mapName, value: k.mapName};
            const newArr = this.mapNames.filter(function(item: SelectItem) {
                return item.value === k.mapName;
            });
            if (newArr === undefined || newArr.length === 0) {
                this.mapNames.push(beanItem);
            }
        });
    }

    populateBeanNamesFilter(data) {
        this.beanNames = [];
        this.beanNames.push({label: 'All Instances', value: null});

        data.forEach((k) => {
            const beanItem: SelectItem = {label: k.beanName, value: k.beanName};
            const newArr = this.beanNames.filter(function(item: SelectItem) {
                return item.value === k.beanName;
            });
            if (newArr === undefined || newArr.length === 0) {
                this.beanNames.push(beanItem);
            }
        });
    }

    evictSelectedKeys() {

        const keysTmp = [];
        if (this.selectedKeysForEviction !== undefined && this.selectedKeysForEviction !== null
            && this.selectedKeysForEviction.length > 0) {
            for (const key of this.selectedKeysForEviction) {
                keysTmp.push(key.key);
            }
        }
        const evictionRequest = {
            bean: 'all',
            keys: keysTmp
        };
        this.keysService.evictKeys(evictionRequest)
            .subscribe(response => {
                this.loadData();
            });
    }

    evictForTerm() {

        const evictionRequest = {
            bean: 'all',
            keys: [this.keyTermForEviction]
        };
        this.keysService.evictKeys(evictionRequest)
            .subscribe(response => {
                this.loadData();
            });
    }

    public toInt(num: string) {
        return +num;
    }
}
