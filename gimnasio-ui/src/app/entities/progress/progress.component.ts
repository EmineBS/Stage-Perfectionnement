import { Component, OnInit, ViewChild, Input } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { IBadge, IBadgeProgress } from 'app/entities/badge/badge.model';
import { series } from './data';
import {
  ChartComponent,
  ApexAxisChartSeries,
  ApexChart,
  ApexXAxis,
  ApexDataLabels,
  ApexStroke,
  ApexYAxis,
  ApexTitleSubtitle,
  ApexLegend,
} from 'ng-apexcharts';
import { CriteriaService, EntityArrayResponseTypeBadgeProgress } from 'app/entities/criteria/service/criteria.service';
export type ChartOptions = {
  series: ApexAxisChartSeries;
  chart: ApexChart;
  xaxis: ApexXAxis;
  stroke: ApexStroke;
  dataLabels: ApexDataLabels;
  yaxis: ApexYAxis;
  title: ApexTitleSubtitle;
  labels: string[];
  legend: ApexLegend;
  subtitle: ApexTitleSubtitle;
};


@Component({
  selector: 'jhi-progress',
  templateUrl: './progress.component.html',
})
export class ProgressComponent implements OnInit {

  badge: IBadge | null = null;

  @ViewChild('chart') chart: ChartComponent;
  public chartOptions!: ChartOptions;
  public chartOptionsList!: ChartOptions[];

  @Input() sectionHeading: string;
  @Input() sectionDescription: string;

  progresss: IBadgeProgress[];

  constructor(
    protected activatedRoute: ActivatedRoute, 
    protected router: Router,
    protected criteriaService: CriteriaService
    ) {
    this.chartOptionsList = [];
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ progress }) => {
      this.progresss = progress;
      this.progresss.forEach(progress => {
        let axisX : string[] | any = progress.progressValues!.map(obj => obj.when);
        let axisY : number[] | any = progress.progressValues!.map(obj => obj.value);   
        this.chartOptionsList.push(this.plot(axisX, axisY, progress.name, progress.description ))
      });
      console.log(" progres data"+ JSON.stringify(this.progresss))
  });
  }

  plot(xAxis : string[]|any, yAxis:number[]|any, title: string|any, subtitle: string|any) : ChartOptions{
    return {
      series: [
        {
          name: 'Value',
          data: yAxis,
        },
      ],
      chart: {
        type: 'area',
        height: 350,
        zoom: {
          enabled: false,
        },
      },
      dataLabels: {
        enabled: false,
      },
      stroke: {
        curve: 'straight',
      },
      title: {
        text: title,
        align: 'left',
        
      },
      subtitle: {
        text: subtitle,
        align: 'left',
      },
      labels: xAxis,
      xaxis: {
        type: 'datetime',
      },
      yaxis: {
        opposite: false,
      },
      legend: {
        horizontalAlign: 'left',
      },
    }
  }
}

