import {
  trigger,
  transition,
  style,
  animate,
  query,
} from '@angular/animations';

export const expandCollapseAnimation = trigger('expandCollapse', [
  transition(':enter', [
    style({
      height: '0px',
      opacity: 0,
      paddingTop: '0px',
      paddingBottom: '0px',
      overflow: 'hidden',
    }),
    animate(
      '300ms ease-out',
      style({ height: '*', opacity: 1, paddingTop: '*', paddingBottom: '*' })
    ),

    query(
      '.content',
      [
        style({ opacity: 0 }),
        animate('200ms 100ms ease-out', style({ opacity: 1 })),
      ],
      { optional: true }
    ),
  ]),
  transition(':leave', [
    query('.content', [animate('100ms ease-in', style({ opacity: 0 }))], {
      optional: true,
    }),
    animate(
      '300ms ease-in',
      style({
        height: '0px',
        opacity: 0,
        paddingTop: '0px',
        paddingBottom: '0px',
      })
    ),
  ]),
]);
