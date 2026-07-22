import { Pipe, PipeTransform } from "@angular/core";

@Pipe({
  name: 'BookTagsPipe',
  standalone: true,
})
export class BookTagsPipe implements PipeTransform {

    transform(value: string): string {
        return value.
        toLocaleLowerCase()
        .split('_')
        .map(word => word.charAt(0).toUpperCase() + word.slice(1))
        .join(' ');
    }
}