import { FC } from "react"

import Head from "next/head"

interface Props {
  title?: string
}

export const Layout: FC<Props>= ({children, title}) => {
  return (
    <>
      <Head>
        <title>{ title || 'Pokemon App' }</title>
        <meta name="author" content="Erick Suarez" />
        <meta name="description" content={`Informacion sobre el pokemon ${ title }`} />
        <meta name="keywords" content={`${title}, pokemon, pokedex`} />
      </Head>

      {/* NavBar */}

      <main>
        { children }
      </main>
    </>
  )
}