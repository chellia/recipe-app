import {User} from './user';
import {Customer} from './customer'

export interface IComplaint {
  id: number;
  number: string;
  name: string;
  description: string;
  detail: string;
  priority: string;
  status: string;
  userDto: User;
  customerDto: Customer;
}
