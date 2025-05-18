export class User {
    id!: string;
    username!: string;
    password!: string;
    passwordConfirmation!: string;
    firstName!: string;
    lastName!: string;
    email!: string;
    captchaToken!: string;
}

export class Login {
    username!: string;
    password!: string;
}