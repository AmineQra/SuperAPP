import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Data, NavigationEnd, Router } from '@angular/router';
import { filter, map, switchMap } from 'rxjs';

@Component({
  selector: 'app-header',
  imports: [CommonModule],
  standalone: true,
  templateUrl: './header.component.html',
  styleUrl: './header.component.css',
})
export class HeaderComponent implements OnInit {
  pageTitle: string = 'Home';
  showSearchBar: Boolean = false;

  constructor(private router: Router, private activatedRoute: ActivatedRoute) {}

  public addSearchBar(data: Data): void {
    this.showSearchBar = data['pageTitle'] == 'Recipes';
  }

  public getPageTitleFromRoute(): void {
    this.router.events
      .pipe(
        filter((event) => event instanceof NavigationEnd),
        map(() => {
          let route = this.activatedRoute.root;
          while (route.firstChild) {
            route = route.firstChild;
          }
          return route;
        }),
        switchMap((route) => route.data)
      )
      .subscribe((data) => {
        this.pageTitle = data['pageTitle'];
        this.addSearchBar(data);
      });
  }

  ngOnInit(): void {
    this.getPageTitleFromRoute();
  }
}
