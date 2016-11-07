export class Issue {
    message: string;
}

export class Result<T> {
    object: T;
    issues: Issue[];

    hasIssues = (): boolean => {
        return this.issues && this.issues.length > 0;
    }
}