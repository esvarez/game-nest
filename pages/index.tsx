import type { NextPage } from 'next'

import { Button } from '@nextui-org/react'

import { Layout } from './components/layouts'

const Home: NextPage = () => {
  return (
    <Layout title='Listado de pokemon'>
      <Button color="gradient">
        Hello mundo
      </Button>
    </Layout>   
  )
}

export default Home
