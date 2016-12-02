import { 
    RequestOptions,
    URLSearchParams
} from '@angular/http';

export function createOptions(params: {name: string, value: any }[]): RequestOptions {
    const searchParams = new URLSearchParams();
    params.forEach(param => {
        searchParams.set(param.name, param.value.toString());
    });
    return new RequestOptions({ search: searchParams });
}